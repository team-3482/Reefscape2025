// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.coral;


import edu.wpi.first.wpilibj2.command.Command;

/** An example command that does nothing. */
public class IntakeCoralCommand extends Command {

    /**
     * Creates a new ExampleCommand.
     */
    public IntakeCoralCommand() {
        setName("IntakeCoralCommand");
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(CoralSubsystem.getInstance());
    }

    // Called when the command is initially scheduled.

    /**
     * Sets the motors to the intake speed
     */
    @Override
    public void initialize() {
        CoralSubsystem.getInstance().intake();
    }

    /**
     * Checks whether the system has a note and stops if it does
     */
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (CoralSubsystem.getInstance().hasCoral_frontLaser()) {
            CoralSubsystem.getInstance().slowIntake();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        CoralSubsystem.getInstance().stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return CoralSubsystem.getInstance().hasCoral_frontLaser() && !CoralSubsystem.getInstance().hasCoral_backLaser();
    }
}
