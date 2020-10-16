package org.panacea.drmp.ree.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ree.REECalculator;
import org.panacea.drmp.ree.domain.exception.REEException;
import org.panacea.drmp.ree.domain.notification.DataNotification;
import org.panacea.drmp.ree.domain.notification.DataNotificationResponse;
import org.panacea.drmp.ree.service.OrchestratorNotificationHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Service
public class OrchestratorNotificationHandlerServiceImpl implements OrchestratorNotificationHandlerService {
    public static final String INVALID_NOTIFICATION_ERR_MSG = "Invalid Data Notification Body.";

    @Autowired
    REECalculator reeCalculator;

    @Override
    @ResponseBody
    public DataNotificationResponse perform(DataNotification notification) throws REEException {
        log.info("[REE] Received notification from orchestrator for Query \""+notification.getQueryId()+"\"");
        try {
            if (notification.getEnvironment() == null) {
                throw new REEException("No environment defined for notification.");
            }
            reeCalculator.evaluateRisk(notification);

            return new DataNotificationResponse(notification.getEnvironment(), notification.getSnapshotId(), notification.getSnapshotTime());
        } catch (REEException e) {
            log.info("REEException occurred: ", e);
            throw new REEException(INVALID_NOTIFICATION_ERR_MSG, e);
        }
    }
}

