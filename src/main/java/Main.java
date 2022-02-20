import cobar_entity.admin.Admin;
import cobar_entity.admin.AdminHelper;
import cobar_entity.appointment.AppointmentTypeDto;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class Main {
  static Admin adminImpl;
  //

  public static void main(String[] args) {
    ORB orb = ORB.init(args, null);
    try {
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContextExt namingContextRef = NamingContextExtHelper.narrow(objRef);
      adminImpl = AdminHelper.narrow(namingContextRef.resolve_str("Admin"));
      adminImpl.addAppointment("MTLA10012020", AppointmentTypeDto.Physician, 5);

    } catch (InvalidName
        | org.omg.CosNaming.NamingContextPackage.InvalidName
        | CannotProceed
        | NotFound e) {
      e.printStackTrace();
    }
  }
}
