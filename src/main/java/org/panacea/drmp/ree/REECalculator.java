package org.panacea.drmp.ree;

import com.google.common.collect.HashBasedTable;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ree.domain.configFiles.AttackerType;
import org.panacea.drmp.ree.domain.exception.REEException;
import org.panacea.drmp.ree.domain.notification.DataNotification;
import org.panacea.drmp.ree.domain.notification.DataNotificationResponse;
import org.panacea.drmp.ree.domain.query.output.QueryLikelihood;
import org.panacea.drmp.ree.domain.serviceLevel.ServiceLevelImpact;
import org.panacea.drmp.ree.service.REEInputRequestService;
import org.panacea.drmp.ree.service.REEOutputPostNotification;
import org.panacea.drmp.ree.service.REEOutputRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class REECalculator {

    @Autowired
    REEInputRequestService reeInputRequestService;
    @Autowired
    REEOutputPostNotification reeOutputPostNotification;
    @Autowired
    REEOutputRequestService reeOutputRequestService;


    private QueryLikelihood queryLikelihood;
    private List<ServiceLevelImpact> serviceLevelImpactList;

    @Synchronized
    public void evaluateRisk(DataNotification notification) {
        log.info("[REE] POST notification to Likelihood Estimation Engine (LEA)");
        DataNotificationResponse likelihoodResponse = reeOutputPostNotification.postLikelihoodRequest(notification);
        log.info("[REE] POST notification to Business Impact Analysis (BIA)");
        DataNotificationResponse impactResponse = reeOutputPostNotification.postImpactRequest(notification);
        if (likelihoodResponse == null) {
            log.error("[REE] Received null response from LEA");
            return;
        }
        if (impactResponse == null) {
            ;
            log.error("[REE] Received null response from BIA");
            return;
        }

        try {
            this.getInput(notification);
        } catch (REEException e) {
            log.error(e.getMessage());
        }

        // given likelihood and impact for each entry, compute risk
        HashBasedTable<String, AttackerType, Double> sl2risk = HashBasedTable.create();
        double likelihood;
        for (ServiceLevelImpact sli : this.serviceLevelImpactList) {
            for (AttackerType a : AttackerType.values()) {
                String target_a = sli.target.get(a);
                if (target_a == null) likelihood = 0.0;
                else likelihood = this.queryLikelihood.getLikelihood(target_a, a);
                sl2risk.put(sli.serviceLevelId, a, sli.impact * likelihood);
            }
        }

        reeOutputRequestService.postRiskProfile(notification, sl2risk);
    }

    private void getInput(DataNotification notification) {
        this.queryLikelihood = reeInputRequestService.getLikelihoodQueryFile(notification);
        log.info("[REE] GET Likelihood values from http://172.16.100.131:8108/persistence/output");
        this.serviceLevelImpactList = reeInputRequestService.getImpactQueryFile(notification);
        log.info("[REE] GET Impact values from http://172.16.100.131:8108/persistence/query/output");
    }
}
