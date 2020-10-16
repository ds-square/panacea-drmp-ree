package org.panacea.drmp.ree.service;

import com.google.common.collect.HashBasedTable;
import org.panacea.drmp.ree.domain.notification.DataNotification;

public interface REEOutputRequestService {
    void postRiskProfile(DataNotification notification, HashBasedTable riskProfile);
}
