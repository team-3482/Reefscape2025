// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.constants;

import edu.wpi.first.wpilibj.util.Color;

/**
 * Constants used throughout the code that are not categorized in other constants files.
 */
public final class Constants {
    /**
     * Tab names in Shuffleboard.
     */
    public static final class ShuffleboardTabNames {
        public static final String DEFAULT = "Competition";
        public static final String UTILITIES = "Utilities";
    }

    /** Constants for the controller and any controller related assignments. */
    public static final class ControllerConstants {
        /** DriverStation ID of the driver controller. */
        public static final int DRIVER_CONTROLLER_ID = 0;
        /** DriverStation ID of the operator controller. */
        public static final int OPERATOR_CONTROLLER_ID = 1;
        /** Removes input around the joystick's center (eliminates stick drift). */
        public static final double DEADBAND = 0.075;
        /** Whether or not to accept directional pad input for movement. */
        public static final boolean DPAD_DRIVE_INPUT = true;
        /** Speed multiplier when using fine-control movement. */
        public static final double FINE_CONTROL_MULT = 0.15;
    }
  
    /** Constants used for auto-aligning the robot when scoring. */
    public static final class AligningConstants {
        public static final class Reef {
            /** How far (in meters) the robot should be from the tag perpendicularly to score. */
            public static final double PERPENDICULAR_DIST_TO_TAG = 0.42;
            /** How far (in meters) the robot should be parallel to the tag to score. */
            public static final double PARALLEL_DIST_TO_TAG = 0.2;
        }

        public static final class Processor {
            /** How far (in meters) the robot should be from the tag perpendicularly to score. */
            public static final double PERPENDICULAR_DIST_TO_TAG = 0.0; // TODO : Find this value in testing
            /** How far (in meters) the robot should be parallel to the tag to score. */
            public static final double PARALLEL_DIST_TO_TAG = 0.0;
        }
    }

    /** Constants for elevator heights */
    public static final class ScoringConstants {
        /* Heights in elevator meters for scoring at these heights. */
        public static final double L1_CORAL = 0.21;
        public static final double L2_CORAL = 0.33;
        public static final double L2_ALGAE = 0.63;
        public static final double L3_CORAL = 0.74;
        public static final double BOTTOM_HEIGHT = 0;
        public static final double MAX_HEIGHT = 0.75;
    }

    /** Colors used with the LEDSubsystem, these are named for readability and priority. */
    public static enum StatusColors {
        OFF(Color.kBlack, -1, -1),
        CORAL(Color.kWhite, 0, -1),
        CAN_ALIGN(Color.kBlue, 1, 0.2),
        ERROR(Color.kRed, 2, 1),
        OK(Color.kGreen, 2, 3),
        RSL(Color.kOrange, 5, -1),
        ;
        
        /** The Color. */
        public final Color color;
        /** The priority of this color over other colors (higher = higher priority). */
        public final int priority;
        /** How long this color should stay. -1 is infinitely long. */
        public final double stickyTime;

        /**
         * Creates a new StatusColors.
         * @param color - The Color.
         * @param priority - The priority of this color over other colors (higher = higher priority).
         * -1 will always be overriden and will always override.
         * @param stickyTime - How long this color should stay. -1 is infinitely long.
         */
        private StatusColors(Color color, int priority, double stickyTime) {
            this.color = color;
            this.priority = priority;
            this.stickyTime = stickyTime;
        }

        /**
         * Get the StatuColors associated with this color.
         * @param color - The color to fetch.
         * @return The StatusColors. If none, returns null.
         */
        public static StatusColors getColor(Color color) {
            for (StatusColors statusColor : StatusColors.values()) {
                if (statusColor.color.equals(color)) {
                    return statusColor;
                }
            }
            return null;
        }
    }
}
