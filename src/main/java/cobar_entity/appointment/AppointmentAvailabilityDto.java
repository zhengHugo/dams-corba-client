package cobar_entity.appointment;


/**
* appointment/AppointmentAvailabilityDto.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Patient.idl
* Sunday, February 20, 2022 4:56:02 PM EST
*/

public final class AppointmentAvailabilityDto implements org.omg.CORBA.portable.IDLEntity
{
  public String appointmentId = null;
  public int availability = (int)0;

  public AppointmentAvailabilityDto ()
  {
  } // ctor

  public AppointmentAvailabilityDto (String _appointmentId, int _availability)
  {
    appointmentId = _appointmentId;
    availability = _availability;
  } // ctor

} // class AppointmentAvailabilityDto
