import java.util.ArrayList;
import java.util.Scanner;

public class Interfaces {
    public static void menuPrincipal(ArrayList<Cliente> catalogoClientes, ArrayList<Pedido> catalogoPedidos,
            ArrayList<Producto> catalogoProductos) {
        Cliente.agregadorClientesManual(catalogoClientes);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            imprimirMenuPrincipal();
            String input = scanner.nextLine();
            if (input.equals("1")) {
                Main.gestionDeUsuarios(catalogoClientes);
            } else if (input.equals("2")) {
                if (catalogoClientes.isEmpty()) {
                    System.out.println("Se deben crear usuarios antes.");
                } else {
                    Cliente cliente = Main.seleccionarCliente(catalogoClientes);
                    Main.gestionDePedidos(cliente, catalogoPedidos, catalogoProductos);
                }
            } else if (input.equals("0")) {
                System.exit(0);
            } else {
                System.out.println("Parámetro " + input + " no reconocido.");
            }
        }
    }
   
    public static void imprimirMenuPrincipal() {
        System.out.println();
        // Panel de administrador.
        System.out.println("***** Menú Principal ******");
        System.out.println("1 para administrar usuarios.");
        System.out.println("2 para usar plataforma de compras.");
        System.out.println("0 Para salir.");
    }
    
    public static void imprimirMenuCliente() {
        System.out.println();
        System.out.println("***** Menú Gestión de usuarios ******");
        System.out.println("Pulsa 1 para agregar nuevo cliente");
        System.out.println("Pulsa 2 para listar clientes");
        System.out.println("Pulsa 0 para volver al menú principal");
    }

    public static void imprimirMenuGestionPedidos() {
        System.out.println();
        System.out.println("***** Menú Gestión de pedidos ******");
        System.out.println("Pulsa 1 para agregar productos al pedido");
        System.out.println("Pulsa 2 para borrar productos del pedido");
        System.out.println("Pulsa 3 para confirmar pedido");
        System.out.println("Pulsa 0 para volver al menú pricipal");
    }
  
    public static void imprimirProductos(ArrayList<Producto> listaProductos) {
        for (int index = 0; index < listaProductos.size(); index++) {
            System.out.println(index + 1 + "\t" + (Producto) listaProductos.get(index));
        }
    }

    public static void imprimirPosiblesPagos() {
        int index = 1;
        for (TipoPago pago : TipoPago.values()) {
            System.out.println(index + " para pagar con " + pago);
            index++;
        }
    }

}
