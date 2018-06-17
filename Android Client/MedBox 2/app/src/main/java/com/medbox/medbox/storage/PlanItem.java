package com.medbox.medbox.storage;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by luzhoutao on 2017/3/8.
 */

public class PlanItem extends Model {

    @Column(name = "planId")
    public int plan_id;

}
