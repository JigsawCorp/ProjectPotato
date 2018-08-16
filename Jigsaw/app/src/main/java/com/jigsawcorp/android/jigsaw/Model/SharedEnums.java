package com.jigsawcorp.android.jigsaw.Model;

import com.jigsawcorp.android.jigsaw.ProjectPotato;
import com.jigsawcorp.android.jigsaw.R;
import android.app.Application;

public class SharedEnums {
    public static enum MuscleGroups {
        ABS(R.string.muscle_group_abs),
        BACK(R.string.muscle_group_back),
        BICEPS(R.string.muscle_group_biceps),
        CALVES(R.string.muscle_group_calves),
        CARDIO(R.string.muscle_group_cardio),
        CHEST(R.string.muscle_group_chest),
        FOREARMS(R.string.muscle_group_forearms),
        LEGS(R.string.muscle_group_legs),
        SHOULDERS(R.string.muscle_group_shoulders),
        TRICEPS(R.string.muscle_group_triceps);

        private int resourceId;

        private MuscleGroups(int id) {
            resourceId = id;
        }

        @Override
        public String toString() {
            return ProjectPotato.getAppContext().getString(resourceId);
        }
    }
}
