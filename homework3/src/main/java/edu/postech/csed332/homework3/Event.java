package edu.postech.csed332.homework3;

public sealed interface Event permits NumberEvent, ActivationEvent {
}

record NumberEvent(int number, boolean set) implements Event {
}

record ActivationEvent(boolean activated) implements Event {
}