
package org.panacea.drmp.ree.domain.serviceLevel;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Dependency {

    private List<Dependency> dependencies;
    private String dependencyType;
    private String serviceLevelId;

}
