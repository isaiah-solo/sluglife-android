package slugapp.com.sluglife.models;

import slugapp.com.sluglife.enums.MarkerTypeEnum;

/**
 * Created by isaiah on 6/17/16
 * <p/>
 * This file contains a facility object.
 */
public class FacilityObject extends BaseObject {
    private MarkerTypeEnum mType;

    /**
     * Constructor
     *
     * @param type Facility type
     */
    public FacilityObject(MarkerTypeEnum type) {
        this.mType = type;
    }

    /**
     * Checks if type given
     *
     * @param type Facility type
     * @return Boolean if facility is of facility type
     */
    public boolean isType(MarkerTypeEnum type) {
        return this.mType == type;
    }
}