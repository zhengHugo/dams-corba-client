package cobar_entity.admin;


/**
* admin/AdminHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Patient.idl
* Sunday, February 20, 2022 4:56:02 PM EST
*/

abstract public class AdminHelper
{
  private static String  _id = "IDL:admin/Admin:1.0";

  public static void insert (org.omg.CORBA.Any a, Admin that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Admin extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (AdminHelper.id (), "Admin");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Admin read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_AdminStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Admin value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static Admin narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Admin)
      return (Admin)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _AdminStub stub = new _AdminStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static Admin unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Admin)
      return (Admin)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _AdminStub stub = new _AdminStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
