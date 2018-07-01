package com.jigsawcorp.android.jigsaw.Database;

// Schema for the Database
public class DatabaseSchema {

    // Table for all the exercises the user can perform. This table holds both default and user-created exercises
    public static final class ExercisesTable {
        public static final String NAME = "Exercises";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String CATEGORY = "category";
            public static final String PERFORMED_EXERCISES = "performed_exercises";
        }
    }

    // Table for Workouts completed by the user
    public static final class WorkoutsTable {
        public static final String NAME = "Workouts";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String START_DATE = "start_date";
            public static final String END_DATE = "end_date";
            public static final String PERFORMED_EXERCISES = "performed_exercises";
            public static final String NOTES = "notes";
        }
    }

    // Key-value Table to hold various configuration options. Made mostly of boolean flags to determine the need to perform a specific action
    public static final class ConfigurationTable {
        public static final String NAME = "ConfigurationOptions";

        public static final class Cols {
            public static final String CONFIGURATION_OPTION = "configuration_option";
            public static final String VALUE = "value";
        }
    }
}
