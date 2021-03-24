import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Pedido {

    private Cliente cliente;
    private Date fechaHora;
    private ArrayList<Producto> listaProductos;
    private double importe;
    private PasarelaDePago pago;
    private EstadoPedido estadoPedido;

    // Constuctor con fecha 
    public Pedido(Cliente cliente, Date fechaHora) {
        this.cliente = cliente;
        this.fechaHora = fechaHora;
        // al crear un pedido la lista de productos siempre es vacia
        this.listaProductos = new ArrayList<Producto>();
        this.pago = null;
        this.estadoPedido = EstadoPedido.INPAGADO;
    }
    // Constuctor sin fecha (se coge al pagar)
    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.fechaHora = null;
        // al crear un pedido la lista de productos siempre es vacia
        this.listaProductos = new ArrayList<Producto>();
        this.pago = null;
        this.estadoPedido = EstadoPedido.INPAGADO;
    }

    
    /** 
     * @param tipoPago
     */
    public void Pagar(TipoPago tipoPago){
        // Una vez realizado el pago no se puede volver a pagar
        // Si no existe pago, pago será null
        if (this.pago == null){
            this.pago = new PasarelaDePago(this.importe);
            pago.Pagar(tipoPago);
            this.estadoPedido = EstadoPedido.PAGADO;
            if ( this.fechaHora == null){
                // En caso de no asignar ninguna fecha y hora concreta, por defecto se rellenará con la fecha y hora actuales en el momento de finalizar el pedido (es decir, cuando lo pagamos)
                this.fechaHora = new Date();
            }
            System.out.println("Pedido realizado con éxito.");
        }else{
            System.out.println("El pago ya ha sido realizado.");
        }
    }

    
    /** 
     * @param producto
     */
    public void agregarProducto(Producto producto) {
        // solo podemos agregar mientas el estado es  inpagado
        if (this.estadoPedido == EstadoPedido.INPAGADO){
            // recalculams importe
                // this.importe = this.importe + producto.getPrecio();
                setImporte(getImporte() + producto.getPrecio());
            // agregar producto 
                this.listaProductos.add(producto);
        }
    }

    
    /** 
     * @param posicion
     */
    public void eliminarProducto(Integer posicion) {
        // recalculamos importe
            // Producto producto_a_eliminar = this.listaProductos.get(posicion);
            // this.importe = this.importe - producto_a_eliminar.getPrecio();
            setImporte( getImporte() - this.listaProductos.get(posicion).getPrecio());
        
            this.listaProductos.remove((int) posicion);
    }

    
    /** 
     * @return boolean
     */
    public boolean pedidoVacio(){
        return this.listaProductos.isEmpty();

    }

    
    /** 
     * @return Cliente
     */
    public Cliente getCliente() {
        return this.cliente;
    }

    
    /** 
     * @param cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
    /** 
     * @return Date
     */
    public Date getFechaHora() {
        return this.fechaHora;
    }

    
    /** 
     * @param fechaHora
     */
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    
    /** 
     * @return ArrayList<Producto>
     */
    public ArrayList<Producto> getListaProductos() {
        return this.listaProductos;
    }

    
    /** 
     * @param listaProductos
     */
    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    
    /** 
     * @return double
     */
    public double getImporte() {
        return this.importe;
    }

    
    /** 
     * @param importe
     */
    public void setImporte(double importe) {
        this.importe = Double.parseDouble(String.format("%.2f", importe));
    }

    public void imprimirProductosDelPedido(){
        if (! this.listaProductos.isEmpty()){
            System.out.println("\t Productos añadidos al pedido: " + this.listaProductos);
        }else{
            System.out.println("\t Lista de pedido vacia");
        }
    }

    
    /** 
     * @return String
     */
    // @Override
    // public String toString() {
    //     String pedido = "\n\tCliente:" + getCliente() + "\n\tFecha: " + getFechaHora() + "\n\tProductos: "
    //             + getListaProductos() +"\n";
    //     return pedido;
    // }

    @Override
    public String toString() {        
        HashMap <Producto, Integer> hashMapProductos = new HashMap<>();
        for (int index = 0; index < getListaProductos().size(); index++) {
            Producto productoActual = (Producto) getListaProductos().get(index);
            if (hashMapProductos.get(productoActual) != null){
                int numeroRepeticiones = hashMapProductos.get(productoActual);
                hashMapProductos.put(productoActual, numeroRepeticiones + 1);
            }else{
                hashMapProductos.put(productoActual, 1);
            }            
        }

        String pedido = "";
        pedido += "Resumen del pedido:\n";
        pedido += "CANT.\tPRODUCTO\t\tPRECIO UD.\tTOTAL\n";
        pedido += "=====\t========\t\t==========\t=====\n";
        for (HashMap.Entry<Producto, Integer> elemento : hashMapProductos.entrySet()){
            int cantidad = elemento.getValue();
            String nombreProducto = elemento.getKey().getNombre();
            double precioProducto = elemento.getKey().getPrecio();
            double totalPrecioPorProducto = precioProducto*cantidad;
            if (nombreProducto.length()<15){
                pedido += cantidad+"\t"+nombreProducto+"\t\t"+precioProducto+"\t\t"+totalPrecioPorProducto+"\n";
            }else{
                pedido += cantidad+"\t"+nombreProducto+"\t"+precioProducto+"\t\t"+totalPrecioPorProducto+"\n";
            }
            
        }
        pedido += "TOTAL: "+ getImporte() + " €\n";
        return pedido;
    }

}

