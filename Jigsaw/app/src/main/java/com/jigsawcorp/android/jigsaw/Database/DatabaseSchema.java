package com.jigsawcorp.android.jigsaw.Database;

// Schema for the Database
public class DatabaseSchema {

    // Single row Table to keep user data intact
    public static final class UserTable {
        public static final String NAME = "user";

        public static final class Cols {
            public static final String DATE_OF_BIRTH = "date_of_birth";
            public static final String ACTIVE_WORKOUT = "active_workout";
        }
    }

    // Table for all the exercises the user can perform. This table holds both default and user-created exercises
    public static final class ExercisesTable {
        public static final String NAME = "exercises";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String CATEGORY = "category";
            public static final String PERFORMED_EXERCISES = "performed_exercises";
        }
    }

    // Table for Workouts completed by the user
    public static final class WorkoutsTable {
        public static final String NAME = "workouts";

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
        public static final String NAME = "configuration_options";

        public static final class Cols {
            public static final String CONFIGURATION_OPTION = "configuration_option";
            public static final String VALUE = "value";
        }
    }

    public static final class PerformedExerciseTable {
        public static final String NAME = "performed_exercises";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String EXERCISE = "exercise";
            public static final String WORKOUT = "workout";
            public static final String START_DATE = "start_date";
            public static final String END_DATE = "end_date";
            public static final String SETS = "sets";
            public static final String NOTES = "notes";
        }
    }

    public static final class ProgramTable {
        public static final String NAME = "programs";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String PROGRAM_WORKOUTS = "program_workouts";
            public static final String TRAINING_TYPE = "training_type";
            public static final String DIFFICULTY_LEVEL = "difficulty_level";
            public static final String SPLIT_TYPE = "split_type";
            public static final String IS_DAY_BASED = "is_day_based";
            public static final String DURATION = "duration";
            public static final String DAYS_PER_WEEK = "days_per_week";
            public static final String DESCRIPTION = "description";
        }
    }
}
