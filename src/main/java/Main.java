import clients.AdminClient;
import clients.PatientClient;
import java.util.Scanner;
import model.appointment.AppointmentType;
import org.omg.CORBA.ORB;

public class Main {

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    ORB orb = ORB.init(args, null);
    System.out.println("Enter client id:");
    String clientId = scanner.nextLine();

    if (clientId.charAt(3) == 'A') {
      enterAdminMainLoop(new AdminClient(clientId, orb));
    } else if (clientId.charAt(4) == 'P') {
      enterPatientMainLoop(new PatientClient(clientId, orb));
    }
  }

  private static void enterAdminMainLoop(AdminClient adminClient) {
    while (true) {
      System.out.println("Options: type number and arguments; separate by space");
      System.out.println("1. addAppointment(appointmentId, appointmentType, capacity)");
      System.out.println("2. removeAppointment(appointmentId, appointmentType)");
      System.out.println("3. listAppointmentAvailability(appointmentType)");
      System.out.println("4. exit");

      String[] input = scanner.nextLine().split(" ");
      switch (input[0]) {
        case "1":
          adminClient.addAppointment(
              input[1], AppointmentType.valueOf(input[2]), Integer.parseInt(input[3]));
          break;
        case "2":
          adminClient.removeAppointment(input[1], AppointmentType.valueOf(input[2]));
          break;
        case "3":
          adminClient.listAppointmentAvailability(AppointmentType.valueOf(input[1]));
          break;
        case "4":
          return;
        default:
          System.out.println("Invalid option.");
          break;
      }
    }
  }

  private static void enterPatientMainLoop(PatientClient patientClient) {
    while (true) {
      System.out.println("Options: type number and arguments; separate by space");
      System.out.println("1. bookAppointment(appointmentId, appointmentType)");
      System.out.println("2. getAppointmentSchedule()");
      System.out.println("3. cancelAppointment(appointmentId, appointmentType)");
      System.out.println(
          "4. swapAppointment(oldAppointmentId, oldAppointmentType, newAppointmentId, newAppointmentType)");
      System.out.println("5. exit");

      String[] input = scanner.nextLine().split(" ");
      switch (input[0]) {
        case "1":
          patientClient.bookAppointment(input[1], AppointmentType.valueOf(input[2]));
          break;
        case "2":
          patientClient.getAppointmentSchedule();
          break;
        case "3":
          patientClient.cancelAppointment(input[1], AppointmentType.valueOf(input[2]));
          break;
        case "4":
          patientClient.swapAppointment(
              input[1],
              AppointmentType.valueOf(input[2]),
              input[3],
              AppointmentType.valueOf(input[4]));
          return;
        case "5":
          return;
        default:
          System.out.println("Invalid option.");
          break;
      }
    }
  }
}
