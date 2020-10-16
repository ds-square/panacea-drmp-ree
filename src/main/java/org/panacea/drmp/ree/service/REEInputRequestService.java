package org.panacea.drmp.ree.service;


import org.panacea.drmp.ree.domain.notification.DataNotification;
import org.panacea.drmp.ree.domain.query.input.QueryInput;
import org.panacea.drmp.ree.domain.query.output.QueryLikelihood;
import org.panacea.drmp.ree.domain.serviceLevel.ServiceLevelImpact;

import java.util.List;


public interface REEInputRequestService {


    QueryLikelihood getLikelihoodQueryFile(DataNotification notification);

    QueryInput getQueryInputFile(DataNotification notification);

    List<ServiceLevelImpact> getImpactQueryFile(DataNotification notification);

}
