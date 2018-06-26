package com.jigsawcorp.android.jigsaw.Database;

public class DatabaseSchema {
    public static final class ExercisesTable {
        public static final String NAME = "Exercises";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String CATEGORY = "category";
            public static final String PERFORMED_EXERCISES = "performed_exercises";
        }
    }

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
}
