// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.SignalLogger;

import java.io.File;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.constants.Constants.NamedColors;
import frc.robot.led.LEDSubsystem;

public class Robot extends LoggedRobot {
    private Command auton;

    @SuppressWarnings({ "resource", "unused" })
    public Robot() {
        RobotContainer robotContainer = RobotContainer.getInstance();
        robotContainer.configureDriverBindings();
        robotContainer.configureOperatorBindings();

        Logger.recordMetadata("ProjectName", "Reefscape2025"); // Set a metadata value

        if (isReal()) {
            String path = "/U/logs/" + (long)(Math.random() * Math.pow(10, 16));

            System.out.println("logging to: " + path + " (new directory: " + new File(path).mkdirs() + ")");

            SignalLogger.setPath(path);
            
            Logger.addDataReceiver(new WPILOGWriter(path)); // Log to a USB stick ("/U/logs")
            Logger.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
            new PowerDistribution(1, ModuleType.kRev); // Enables power distribution logging

            Logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
            // This will always be either 0 or 1, so the > sign is used to suppress the comparing identical expressions.
            Logger.recordMetadata("GitDirty", BuildConstants.DIRTY > 0 ? "true" : "false");
            Logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);

            Logger.start();
        }

        LEDSubsystem.getInstance().blinkColor(Color.kOrange);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        SignalLogger.stop();
        // Blink like the RSL when disabled
        LEDSubsystem.getInstance().blinkColor(Color.kOrange);
    }

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {
        SignalLogger.start();
        LEDSubsystem.getInstance().setColor(Color.kOrange);
    }

    @Override
    public void autonomousInit() {
        this.auton = RobotContainer.getInstance().getAutonomousCommand();

        Logger.recordOutput("Auton/AutonCommand", auton.getName());

        if (this.auton != null) {
            this.auton.schedule();
            LEDSubsystem.getInstance().setColor(NamedColors.OK);
        }
        else {
            System.err.println("No auton command found.");
            LEDSubsystem.getInstance().blinkColor(NamedColors.ERROR);
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {
        if (auton != null) {
            this.auton.cancel();
        }
    } 

    @Override
    public void teleopPeriodic() {}

    @Override
    public void teleopExit() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
        
        // TODO ROBOT BUILT : Find Max Module Speed
        // SwerveSubsystem.getInstance().setControl(
        //     new SwerveRequest.SysIdSwerveTranslation().withVolts(12)
        // );
    }

    // private double topSpeed;
    @Override
    public void testPeriodic() {
        // ChassisSpeeds speeds = SwerveSubsystem.getInstance().getState().Speeds;
        // double speed = Math.sqrt(Math.pow(speeds.vyMetersPerSecond, 2) + Math.pow(speeds.vxMetersPerSecond, 2));
        // topSpeed = Math.max(this.topSpeed, speed);
        // System.out.println(this.topSpeed);
    }

    @Override
    public void testExit() {
        // SwerveSubsystem.getInstance().setControl(new SwerveRequest.SwerveDriveBrake());
    }
}
