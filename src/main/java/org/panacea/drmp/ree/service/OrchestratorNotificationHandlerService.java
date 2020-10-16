package org.panacea.drmp.ree.service;

import org.panacea.drmp.ree.domain.notification.DataNotification;
import org.panacea.drmp.ree.domain.notification.DataNotificationResponse;

public interface OrchestratorNotificationHandlerService {

    DataNotificationResponse perform(DataNotification notification);

}
