package org.panacea.drmp.ree.domain.serviceLevel;


import org.panacea.drmp.ree.domain.configFiles.AttackerType;
import org.panacea.drmp.ree.domain.impact.ImpactLevel;
import org.panacea.drmp.ree.utils.TokensBag;

import java.util.HashMap;
import java.util.Map;

public class ServiceLevelImpact {
    public String serviceLevelId;
    public Double impact;
    public ImpactLevel impactLevel;
    public TokensBag tokens;
    public Map<AttackerType, String> target;

    public ServiceLevelImpact(String sl, Double impact, Map<AttackerType, String> target) {
        this.serviceLevelId = sl;
        this.impact = impact;
        this.target = target;
        this.tokens = new TokensBag();
        if (target == null) {
            this.target = new HashMap<>();
            for (AttackerType a : AttackerType.values()) {
                this.target.put(a, null);
            }
        } else {
            this.target = target;
        }
    }

    @Override
    public String toString() {
        return this.serviceLevelId + "|" + this.impact + "|" + this.impactLevel + "|" + this.target + "|" + this.tokens.toString();
    }
}
