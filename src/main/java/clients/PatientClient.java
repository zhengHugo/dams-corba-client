package clients;

import cobar_entity.patient.Patient;
import cobar_entity.patient.PatientHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import logger.LoggerUtil;
import model.appointment.Appointment;
import model.appointment.AppointmentType;
import model.role.PatientId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class PatientClient {

  private final PatientId id;
  private Patient patientRemote;
  private final Logger logger;
  private final Gson gson = new Gson();
  private final Type appointmentListType = new TypeToken<List<Appointment>>() {}.getType();
  private final ORB orb;

  public PatientClient(String id, ORB orb) {
    this.id = new PatientId(id);
    this.orb = orb;
    LoggerUtil.createLoggerByClientId(this.id);
    logger = LogManager.getLogger("logger." + id);
    setPatientRemote();
  }

  public void bookAppointment(String appointmentIdStr, AppointmentType type) {
    boolean success =
        patientRemote.bookAppointment(this.id.getId(), type.toDto(), appointmentIdStr);
    if (success) {
      logger.info(String.format("Booked appointment: %s - %s", type, appointmentIdStr));
    } else {
      logger.info(String.format("Unable to book appointment: %s - %s", type, appointmentIdStr));
    }
  }

  public List<Appointment> getAppointmentSchedule() {
    List<Appointment> appointments =
        gson.fromJson(patientRemote.getAppointmentSchedule(this.id.getId()), appointmentListType);
    logger.info(
        String.format(
            "Get appointment schedule: %s",
            appointments.stream()
                .map(app -> app.getType() + " - " + app.getAppointmentId().getId() + " ")
                .reduce(String::concat)
                .orElse("")));
    return appointments;
  }

  public boolean cancelAppointment(String id, AppointmentType type) {
    return patientRemote.cancelAppointment(this.id.getId(), type.toDto(), id);
  }

  public boolean swapAppointment(String oldId, AppointmentType oldType, String newId, AppointmentType newType) {
    return patientRemote.swapAppointment(this.id.getId(), oldType.toDto(), oldId, newType.toDto(), newId);
  }

  private void setPatientRemote() {
    try {
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContextExt namingContextRef = NamingContextExtHelper.narrow(objRef);
      patientRemote =
          PatientHelper.narrow(namingContextRef.resolve_str("Patient" + this.id.getCity().code));
    } catch (InvalidName
        | org.omg.CosNaming.NamingContextPackage.InvalidName
        | CannotProceed
        | NotFound e) {
      e.printStackTrace();
    }
  }
}
