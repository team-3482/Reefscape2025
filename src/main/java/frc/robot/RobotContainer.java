// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.algae.AlgaeSubsystem;
import frc.robot.constants.Constants.ControllerConstants;
import frc.robot.constants.Constants.ScoringConstants;
import frc.robot.coral.IntakeCoralCommand;
import frc.robot.coral.OuttakeCoralCommand;
import frc.robot.elevator.MoveElevatorCommand;
import frc.robot.elevator.ElevatorSubsystem;
import frc.robot.elevator.ZeroElevatorCommand;
import frc.robot.led.LEDSubsystem;
import frc.robot.utilities.CommandGenerators;

public class RobotContainer {
    // Thread-safe singleton design pattern.
    private static volatile RobotContainer instance;
    private static Object mutex = new Object();

    public static RobotContainer getInstance() {
        RobotContainer result = instance;
        
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new RobotContainer();
                }
            }
        }
        return instance;
    }

    // private final SendableChooser<Command> autoChooser;
    
    // Instance of the controllers used to drive the robot
    private CommandXboxController driverController;
    private CommandXboxController operatorController;

    public RobotContainer() {
        this.driverController = new CommandXboxController(ControllerConstants.DRIVER_CONTROLLER_ID);
        this.operatorController = new CommandXboxController(ControllerConstants.OPERATOR_CONTROLLER_ID);

        configureDrivetrain(); // This is done separately because it works differently from other Subsystems
        initializeSubsystems();

        // Register named commands for Pathplanner (always do this after subsystem initialization)
        registerNamedCommands();

        // this.autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be Commands.none()
        // Shuffleboard.getTab(ShuffleboardTabNames.DEFAULT)
        //     .add("Auto Chooser", autoChooser)
        //     .withWidget(BuiltInWidgets.kComboBoxChooser)
        //     .withSize(4, 3);
    }

    /**
     * This function initializes the swerve subsystem and configures its bindings with the driver controller.
     * This is based on the {@code Phoenix6 Swerve Example} that can be found on GitHub.
     */
    private void configureDrivetrain() {
        // TODO : Once the drivetrain chassis is built.
    }

    /** Creates instances of each subsystem so periodic always runs. */
    private void initializeSubsystems() {
        LEDSubsystem.getInstance();
        ElevatorSubsystem.getInstance();
    }

    /** Register all NamedCommands for PathPlanner use */
    private void registerNamedCommands() {
        
    }

    /** Configures the button bindings of the driver controller. */
    public void configureDriverBindings() {
        this.driverController.b().onTrue(CommandGenerators.CancelAllCommands());
    }

    /** Configures the button bindings of the operator controller. */
    public void configureOperatorBindings() {
        this.operatorController.b().onTrue(CommandGenerators.CancelAllCommands());

        // Elevator
        this.operatorController.a()
            .onTrue(new MoveElevatorCommand(ScoringConstants.L1_HEIGHT))
            .onFalse(new MoveElevatorCommand(ScoringConstants.BOTTOM_HEIGHT));
        this.operatorController.x()
            .onTrue(new MoveElevatorCommand(ScoringConstants.L2_HEIGHT))
            .onFalse(new MoveElevatorCommand(ScoringConstants.BOTTOM_HEIGHT));
        this.operatorController.y()
            .onTrue(new MoveElevatorCommand(ScoringConstants.L3_HEIGHT))
            .onFalse(new MoveElevatorCommand(ScoringConstants.BOTTOM_HEIGHT));
        this.operatorController.rightStick()
            .onTrue(new ZeroElevatorCommand());

        // Algae
        this.operatorController.pov(90)
            .onTrue(AlgaeSubsystem.getInstance().runOnce(() -> AlgaeSubsystem.getInstance().outtake()))
            .onFalse(AlgaeSubsystem.getInstance().runOnce(() -> AlgaeSubsystem.getInstance().stop()));
        this.operatorController.pov(270)
            .onTrue(AlgaeSubsystem.getInstance().runOnce(() -> AlgaeSubsystem.getInstance().intake()))
            .onFalse(AlgaeSubsystem.getInstance().runOnce(() -> AlgaeSubsystem.getInstance().stop()));

        // Coral
        this.operatorController.leftBumper().whileTrue(new IntakeCoralCommand());
        this.operatorController.rightBumper().whileTrue(new OuttakeCoralCommand());
    }

    /**
     * Gets the instance of the driverController.
     * @return The driver controller.
     */
    public CommandXboxController getDriverController() {
        return this.driverController;
    }

    /**
     * Gets the instance of the operatorController.
     * @return The operator controller.
     */
    public CommandXboxController getOperatorController() {
        return this.operatorController;
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     * @return The command to run in autonomous.
     */
    public Command getAutonomousCommand() {
        return Commands.none(); // this.autoChooser.getSelected();
    }
}
