package frc.robot.utilities;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.swerve.SwerveSubsystem;

/**
 * A class that holds static methods that group Commands under specific names.
 * This is useful because that way RobotContainer.java has less code and more readability.
 */
public final class CommandGenerators {
    // GENERAL
    //
    //
    //
    //
    //
    //
    /**
     * A command that cancels all running commands.
     * @return The command.
     */
    public static Command CancelAllCommands() {
        return Commands.runOnce(() -> CommandScheduler.getInstance().cancelAll());
    }

    // DRIVER
    //
    //
    //
    //
    //
    //
    /**
     * A command that takes the current orientation of the robot
     * and makes it X forward for field-relative maneuvers.
     * @return The command.
     */
    public static Command SetForwardDirectionCommand() {
        return Commands.runOnce(() -> SwerveSubsystem.getInstance().seedFieldCentric());
    }

    /**
     * A command that resets the odometry using data from the Limelights.
     * @return The command.
     * @apiNote If the Limelight returns an empty Pose2d, this command is a no-op.
     * @deprecated Vision code keeps up fast enough for this to not be necessary. This method will set the robot to an empty Pose2d.
     */
    @Deprecated
    public static Command ResetPoseUsingLimelightCommand() {
        return Commands.runOnce(() -> {
            Pose2d estimatedPose = Pose2d.kZero;
            SwerveSubsystem.getInstance().resetPose(estimatedPose);
        });
    }

    // OPERATOR
    //
    //
    //
    //
    //
    //
}