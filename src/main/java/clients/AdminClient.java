package clients;

import cobar_entity.admin.Admin;
import cobar_entity.admin.AdminHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.util.List;
import logger.LoggerUtil;
import model.appointment.AppointmentAvailability;
import model.appointment.AppointmentType;
import model.role.AdminId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class AdminClient {

  private final AdminId id;
  private Admin adminRemote;
  private final Logger logger;
  private final Gson gson = new Gson();
  private final Type appointmentAvailabilityListType =
      new TypeToken<List<AppointmentAvailability>>() {}.getType();
  private final ORB orb;

  public AdminClient(String id, ORB orb) {
    this.id = new AdminId(id);
    LoggerUtil.createLoggerByClientId(this.id);
    logger = LogManager.getLogger("logger." + id);
    this.orb = orb;
    setAdminRemote();
  }

  public void addAppointment(String id, AppointmentType type, int capacity) {
    boolean success = adminRemote.addAppointment(id, type.toDto(), capacity);
    if (success) {
      logger.info(String.format("Added appointment: %s - %s", type, id));
    } else {
      logger.info(String.format("Unable to add appointment %s - %s", type, id));
    }
  }

  public void removeAppointment(String id, AppointmentType type) {
    String message = adminRemote.removeAppointment(id, type.toDto());
    logger.info(message);
  }

  public void listAppointmentAvailability(AppointmentType type) {
    List<AppointmentAvailability> availabilities =
        gson.fromJson(
            adminRemote.listAppointmentAvailability(type.toDto()), appointmentAvailabilityListType);
    StringBuilder stringBuilder = new StringBuilder("Appointment availabilities of ");
    stringBuilder.append(type).append(" - ");
    for (AppointmentAvailability availability : availabilities) {
      stringBuilder
          .append(availability.getAppointmentId())
          .append(" ")
          .append(availability.getAvailability())
          .append(", ");
    }
    // replace the last ", " with "."
    stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), ".");
    logger.info(stringBuilder.toString());
  }

  private void setAdminRemote() {
    try {
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContextExt namingContextRef = NamingContextExtHelper.narrow(objRef);
      adminRemote =
          AdminHelper.narrow(namingContextRef.resolve_str("Admin" + this.id.getCity().code));
    } catch (InvalidName
        | org.omg.CosNaming.NamingContextPackage.InvalidName
        | CannotProceed
        | NotFound e) {
      e.printStackTrace();
    }
  }
}
