import java.io.*;
import java.util.*;

public class AgendaTelefonica {
    private static final String FILE_NAME = "agenda.dat";
    private static List<Contacto> agenda = new ArrayList<>();

    public static void main(String[] args) {
        cargarAgenda();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    agregarContacto(scanner);
                    break;
                case 2:
                    eliminarContacto(scanner);
                    break;
                case 3:
                    mostrarAgenda();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no valida.");
            }
        } while (opcion != 4);

        guardarAgenda();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Agenda Telefónica ---");
        System.out.println("1. Agregar contacto");
        System.out.println("2. Eliminar contacto");
        System.out.println("3. Mostrar agenda");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void agregarContacto(Scanner scanner) {
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();
        agenda.add(new Contacto(nombre, telefono));
        System.out.println("Contacto agregado.");
    }

    private static void eliminarContacto(Scanner scanner) {
        System.out.print("Ingrese el nombre del contacto a eliminar: ");
        String nombre = scanner.nextLine();
        boolean encontrado = false;

        Iterator<Contacto> iterator = agenda.iterator();
        while (iterator.hasNext()) {
            Contacto contacto = iterator.next();
            if (contacto.getNombre().equalsIgnoreCase(nombre)) {
                iterator.remove();
                encontrado = true;
                System.out.println("Contacto eliminado.");
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Contacto no encontrado.");
        }
    }

    private static void mostrarAgenda() {
        if (agenda.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            System.out.println("\n--- Lista de Contactos ---");
            for (Contacto contacto : agenda) {
                System.out.println(contacto);
            }
        }
    }

    private static void cargarAgenda() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            agenda = (List<Contacto>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de la agenda. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la agenda: " + e.getMessage());
        }
    }

    private static void guardarAgenda() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(agenda);
        } catch (IOException e) {
            System.out.println("Error al guardar la agenda: " + e.getMessage());
        }
    }
}

class Contacto implements Serializable {
    private String nombre;
    private String telefono;

    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}