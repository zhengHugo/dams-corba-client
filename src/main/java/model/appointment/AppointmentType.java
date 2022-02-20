package model.appointment;

public enum AppointmentType{
  Physician(0),
  Surgeon(1),
  Dental(2);

  private int value;

  AppointmentType(int value){
    this.value = value;
  }

  public static AppointmentType getByInt(int value) {
    if (value == 0) return Physician;
    else if (value == 1) return Surgeon;
    else if (value == 2) return Dental;
    else return null;
  }
}
