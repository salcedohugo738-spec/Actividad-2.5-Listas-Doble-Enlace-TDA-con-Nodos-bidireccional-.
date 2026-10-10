/*
Morales Escobar Juan Adrian
Salcedo Alvarez Hugo Emmanuel
 */
package front_end;
import Nodos.ListaBidireccional;
import Nodos.Nodo;
import back_end.Pagina;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class GUI_Historial extends javax.swing.JFrame {

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    // TDA Lista Bidireccional (capa 2) que alimenta toda la interfaz
    private final ListaBidireccional lista = new ListaBidireccional();
    // Posición del nodo resaltado en el panel de visualización (-1 = ninguno)
    private int posResaltada = -1;

    /** Dibuja la lista completa: null <-> [nodo] <-> [nodo] <-> null */
    private void renderizarLista() {
        panelContenedorLista.removeAll();

        panelContenedorLista.add(new javax.swing.JLabel(" null \u21c4 "));
        Nodo actual = lista.getPrimero();
        int i = 0;
        int ultimo = lista.obtenerTamano() - 1;
        while (actual != null) {
            Pagina pagina = actual.getDato();
            String etiqueta = "Pos " + i;
            String rol = (i == 0 && i == ultimo) ? "primero y \u00faltimo" : (i == 0 ? "primero" : (i == ultimo ? "\u00faltimo" : ""));

            javax.swing.JPanel nodoPanel = new javax.swing.JPanel();
            nodoPanel.setLayout(new javax.swing.BoxLayout(nodoPanel, javax.swing.BoxLayout.Y_AXIS));
            nodoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(etiqueta));
            nodoPanel.add(new javax.swing.JLabel(pagina.titulo()));
            if (!rol.isEmpty()) {
                nodoPanel.add(new javax.swing.JLabel("(" + rol + ")"));
            }
            nodoPanel.add(new javax.swing.JLabel(pagina.url()));
            nodoPanel.add(new javax.swing.JLabel(pagina.hora().format(FORMATO_HORA)));
            if (i == posResaltada) {
                nodoPanel.setBackground(new java.awt.Color(255, 235, 130));
            }
            panelContenedorLista.add(nodoPanel);
            if (actual.getSiguiente() != null) {
                panelContenedorLista.add(new javax.swing.JLabel(" \u21c4 "));
            }

            actual = actual.getSiguiente();
            i++;
        }
        panelContenedorLista.add(new javax.swing.JLabel(lista.estaVacia() ? "" : " \u21c4 "));
        panelContenedorLista.add(new javax.swing.JLabel("null"));

        panelContenedorLista.revalidate();
        panelContenedorLista.repaint();
    }

    /** Valida el formulario y construye el record Pagina con la hora actual. */
    private Pagina leerPagina() {
        if (!validarCamposFormulario()) {
            return null;
        }
        return new Pagina(txtURL.getText(), txtTitulo.getText(), LocalTime.now());
    }

    /** Pide una posición por diálogo. Devuelve null si se cancela o no es válida. */
    private Integer pedirPosicion(String accion, int maximo) {
        String texto = JOptionPane.showInputDialog(this,
                "Posici\u00f3n para " + accion + " (0 a " + maximo + "):", "Posici\u00f3n", JOptionPane.QUESTION_MESSAGE);
        if (texto == null) {
            return null;
        }
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La posici\u00f3n debe ser un n\u00famero entero.",
                    "Posici\u00f3n inv\u00e1lida", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private void error(Exception ex) {
        registrarOperacion("ERROR: " + ex.getMessage());
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Operaci\u00f3n no v\u00e1lida", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarPagina(String titulo, Pagina p, int pos) {
        posResaltada = pos;
        renderizarLista();
        registrarOperacion(titulo + " (pos " + pos + "): " + p.url());
        JOptionPane.showMessageDialog(this,
                "Posici\u00f3n: " + pos + "\nURL: " + p.url() + "\nT\u00edtulo: " + p.titulo()
                + "\nHora: " + p.hora().format(FORMATO_HORA),
                titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /** Mueve el nodo resaltado (cursor) a la posición indicada y muestra su contenido. */
    private void moverCursor(String titulo, int destino) {
        try {
            Pagina p = lista.mostrarEnPosicion(destino);
            posResaltada = destino;
            renderizarLista();
            registrarOperacion(titulo + " \u2192 pos " + destino + ": " + p.titulo() + " (" + p.url() + ")");
        } catch (IllegalStateException | IndexOutOfBoundsException ex) {
            error(ex);
        }
    }

    private void tras(String mensaje) {
        posResaltada = -1;
        registrarOperacion(mensaje);
        renderizarLista();
    }

private boolean validarCamposFormulario() {
    if (txtTitulo.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "El campo 'Título' es obligatorio.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
        txtTitulo.requestFocus();
        return false;
    }
    if (txtURL.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "El campo 'URL' es obligatorio.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
        txtURL.requestFocus();
        return false;
    }
    return true;
}
private void registrarOperacion(String mensaje) {
    java.time.LocalTime horaActual = java.time.LocalTime.now();
    String horaFormateada = horaActual.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
    txtLog.append("[" + horaFormateada + "] " + mensaje + "\n");
    txtLog.setCaretPosition(txtLog.getDocument().getLength());
}
private void limpiarFormulario() {
    txtURL.setText("");
    txtTitulo.setText("");
    txtURL.requestFocus();
}



    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUI_Historial.class.getName());

   
    public GUI_Historial() {
        initComponents();
        renderizarLista();
        registrarOperacion("Lista creada (vac\u00eda).");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelFormulario = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtURL = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        panelLog = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        panelBotones = new javax.swing.JPanel();
        btnAgregarInicio = new javax.swing.JButton();
        btnAgregarFinal = new javax.swing.JButton();
        btnAgregarPosicion = new javax.swing.JButton();
        btnRemoverInicio = new javax.swing.JButton();
        btnRemoverFinal = new javax.swing.JButton();
        btnRemoverPosicion = new javax.swing.JButton();
        btnMostrarInicio = new javax.swing.JButton();
        btnMostrarFinal = new javax.swing.JButton();
        btnMostrarPosicion = new javax.swing.JButton();
        btnMostrarValor = new javax.swing.JButton();
        btnVaciarFila = new javax.swing.JButton();
        btn_MoverDelante = new javax.swing.JButton();
        btn_MoverAtras = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        scrollEstructura = new javax.swing.JScrollPane();
        panelContenedorLista = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Sistema de gestion de historial de navegacion--- Lista Bidireccional");

        javax.swing.GroupLayout panelTituloLayout = new javax.swing.GroupLayout(panelTitulo);
        panelTitulo.setLayout(panelTituloLayout);
        panelTituloLayout.setHorizontalGroup(
            panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTituloLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );
        panelTituloLayout.setVerticalGroup(
            panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTituloLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        getContentPane().add(panelTitulo, java.awt.BorderLayout.PAGE_START);

        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Captura de datos"));

        jLabel2.setText("URL:");

        txtURL.addActionListener(this::txtURLActionPerformed);

        jLabel3.setText("Titulo:");

        txtTitulo.addActionListener(this::txtTituloActionPerformed);

        javax.swing.GroupLayout panelFormularioLayout = new javax.swing.GroupLayout(panelFormulario);
        panelFormulario.setLayout(panelFormularioLayout);
        panelFormularioLayout.setHorizontalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(26, 26, 26)
                        .addComponent(txtURL, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTitulo)))
                .addContainerGap())
        );
        panelFormularioLayout.setVerticalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(118, Short.MAX_VALUE))
        );

        getContentPane().add(panelFormulario, java.awt.BorderLayout.LINE_START);

        panelLog.setBorder(javax.swing.BorderFactory.createTitledBorder("Bitacora"));

        txtLog.setEditable(false);
        txtLog.setColumns(20);
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        javax.swing.GroupLayout panelLogLayout = new javax.swing.GroupLayout(panelLog);
        panelLog.setLayout(panelLogLayout);
        panelLogLayout.setHorizontalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        panelLogLayout.setVerticalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(panelLog, java.awt.BorderLayout.PAGE_END);

        panelBotones.setBorder(javax.swing.BorderFactory.createTitledBorder("Panel Botones"));

        btnAgregarInicio.setText("Agregar Ini");
        btnAgregarInicio.addActionListener(this::btnAgregarInicioActionPerformed);

        btnAgregarFinal.setText("Agregar final");
        btnAgregarFinal.addActionListener(this::btnAgregarFinalActionPerformed);

        btnAgregarPosicion.setText("Agregar Pos");
        btnAgregarPosicion.addActionListener(this::btnAgregarPosicionActionPerformed);

        btnRemoverInicio.setText("Remover Ini");
        btnRemoverInicio.addActionListener(this::btnRemoverInicioActionPerformed);

        btnRemoverFinal.setText("Remover Fin");
        btnRemoverFinal.addActionListener(this::btnRemoverFinalActionPerformed);

        btnRemoverPosicion.setText("Remover Pos");
        btnRemoverPosicion.addActionListener(this::btnRemoverPosicionActionPerformed);

        btnMostrarInicio.setText("Mostrar ini");
        btnMostrarInicio.addActionListener(this::btnMostrarInicioActionPerformed);

        btnMostrarFinal.setText("Mostrar Fin");
        btnMostrarFinal.addActionListener(this::btnMostrarFinalActionPerformed);

        btnMostrarPosicion.setText("Mostrar Pos");
        btnMostrarPosicion.addActionListener(this::btnMostrarPosicionActionPerformed);

        btnMostrarValor.setText("Mostrar Val");
        btnMostrarValor.addActionListener(this::btnMostrarValorActionPerformed);

        btnVaciarFila.setText("Vaciar");
        btnVaciarFila.addActionListener(this::btnVaciarFilaActionPerformed);

        btn_MoverDelante.setText("Mover delante");
        btn_MoverDelante.addActionListener(this::btn_MoverDelanteActionPerformed);

        btn_MoverAtras.setText("Mover atras");
        btn_MoverAtras.addActionListener(this::btn_MoverAtrasActionPerformed);

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_MoverDelante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoverInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarPosicion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarFinal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoverPosicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoverFinal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMostrarInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMostrarFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMostrarPosicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMostrarValor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVaciarFila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBotonesLayout.createSequentialGroup()
                        .addComponent(btn_MoverAtras)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarInicio)
                    .addComponent(btnMostrarInicio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarFinal)
                    .addComponent(btnMostrarFinal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarPosicion)
                    .addComponent(btnMostrarPosicion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoverInicio)
                    .addComponent(btnMostrarValor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoverFinal)
                    .addComponent(btnVaciarFila))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemoverPosicion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_MoverDelante)
                    .addComponent(btn_MoverAtras))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(panelBotones, java.awt.BorderLayout.LINE_END);
        panelBotones.getAccessibleContext().setAccessibleName("");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Panel visualizacion"));

        panelContenedorLista.setLayout(new javax.swing.BoxLayout(panelContenedorLista, javax.swing.BoxLayout.X_AXIS));
        scrollEstructura.setViewportView(panelContenedorLista);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollEstructura)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollEstructura)
                .addContainerGap())
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtURLActionPerformed
        txtTitulo.requestFocus();
    }//GEN-LAST:event_txtURLActionPerformed

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        btnAgregarFinalActionPerformed(evt);
    }//GEN-LAST:event_txtTituloActionPerformed

    private void btnAgregarInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarInicioActionPerformed
        Pagina p = leerPagina();
        if (p == null) return;
        lista.insertarAlPrincipio(p);
        tras("Insertado al principio: " + p.url());
        limpiarFormulario();
    }//GEN-LAST:event_btnAgregarInicioActionPerformed

    private void btnAgregarFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarFinalActionPerformed
        Pagina p = leerPagina();
        if (p == null) return;
        lista.insertarAlFinal(p);
        tras("Insertado al final: " + p.url());
        limpiarFormulario();
    }//GEN-LAST:event_btnAgregarFinalActionPerformed

    private void btnAgregarPosicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPosicionActionPerformed
        Pagina p = leerPagina();
        if (p == null) return;
        Integer pos = pedirPosicion("insertar", lista.obtenerTamano());
        if (pos == null) return;
        try {
            lista.insertarEnPosicion(p, pos);
            tras("Insertado en posici\u00f3n " + pos + ": " + p.url());
            limpiarFormulario();
        } catch (IndexOutOfBoundsException ex) {
            error(ex);
        }
    }//GEN-LAST:event_btnAgregarPosicionActionPerformed

    private void btnRemoverInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverInicioActionPerformed
        try {
            tras("Removido del principio: " + lista.removerAlPrincipio().url());
        } catch (IllegalStateException ex) {
            error(ex);
        }
    }//GEN-LAST:event_btnRemoverInicioActionPerformed

    private void btnRemoverFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverFinalActionPerformed
        try {
            tras("Removido del final: " + lista.removerAlFinal().url());
        } catch (IllegalStateException ex) {
            error(ex);
        }
    }//GEN-LAST:event_btnRemoverFinalActionPerformed

    private void btnRemoverPosicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverPosicionActionPerformed
        if (lista.estaVacia()) {
            error(new IllegalStateException("La lista est\u00e1 vac\u00eda."));
            return;
        }
        Integer pos = pedirPosicion("remover", lista.obtenerTamano() - 1);
        if (pos == null) return;
        try {
            tras("Removido de posici\u00f3n " + pos + ": " + lista.removerEnPosicion(pos).url());
        } catch (IndexOutOfBoundsException ex) {
            error(ex);
        }
    }//GEN-LAST:event_btnRemoverPosicionActionPerformed

    private void btnMostrarInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarInicioActionPerformed
        try {
            mostrarPagina("Mostrar inicio", lista.mostrarAlPrincipio(), 0);
        } catch (IllegalStateException ex) {
            error(ex);
        }
    }//GEN-LAST:event_btnMostrarInicioActionPerformed

    private void btnMostrarFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarFinalActionPerformed
        try {
            mostrarPagina("Mostrar final", lista.mostrarAlFinal(), lista.obtenerTamano() - 1);
        } catch (IllegalStateException ex) {
            error(ex);
        }
    }//GEN-LAST:event_btnMostrarFinalActionPerformed

    private void btnMostrarPosicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarPosicionActionPerformed
        if (lista.estaVacia()) {
            error(new IllegalStateException("La lista est\u00e1 vac\u00eda."));
            return;
        }
        Integer pos = pedirPosicion("mostrar", lista.obtenerTamano() - 1);
        if (pos == null) return;
        try {
            mostrarPagina("Mostrar posici\u00f3n", lista.mostrarEnPosicion(pos), pos);
        } catch (IndexOutOfBoundsException ex) {
            error(ex);
        }
    }//GEN-LAST:event_btnMostrarPosicionActionPerformed

    private void btnMostrarValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarValorActionPerformed
        if (lista.estaVacia()) {
            error(new IllegalStateException("La lista est\u00e1 vac\u00eda."));
            return;
        }
        String url = txtURL.getText().trim();
        if (url.isEmpty()) {
            url = JOptionPane.showInputDialog(this, "URL a buscar:", "Buscar por valor", JOptionPane.QUESTION_MESSAGE);
            if (url == null) return;
            url = url.trim();
        }
        ListaBidireccional.Resultado r = lista.buscarPorValor(url);
        if (r == null) {
            posResaltada = -1;
            renderizarLista();
            registrarOperacion("B\u00fasqueda sin resultados: " + url);
            JOptionPane.showMessageDialog(this, "No se encontr\u00f3 la URL: " + url, "Buscar por valor", JOptionPane.INFORMATION_MESSAGE);
        } else {
            mostrarPagina("Mostrar valor", r.pagina(), r.posicion());
        }
    }//GEN-LAST:event_btnMostrarValorActionPerformed

    private void btnVaciarFilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarFilaActionPerformed
        lista.vaciar();                       // primero = ultimo = null, tamaño = 0
    tras("Lista vaciada.");               // limpia resaltado, escribe en bitácora y redibuja
    limpiarFormulario();

    
    }//GEN-LAST:event_btnVaciarFilaActionPerformed

    private void btn_MoverDelanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MoverDelanteActionPerformed
        if (lista.estaVacia()) {
            error(new IllegalStateException("La lista est\u00e1 vac\u00eda."));
            return;
        }
        int ultimoIndice = lista.obtenerTamano() - 1;
        int destino;
        if (posResaltada < 0 || posResaltada > ultimoIndice) {
            destino = 0;                      // sin cursor: empieza en primero
        } else if (posResaltada == ultimoIndice) {
            registrarOperacion("Ya est\u00e1s en el \u00faltimo nodo (pos " + posResaltada + ").");
            JOptionPane.showMessageDialog(this, "Ya est\u00e1s en el \u00faltimo nodo de la lista.",
                    "Mover adelante", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            destino = posResaltada + 1;       // avanza por el enlace siguiente
        }
        moverCursor("Mover adelante", destino);
    }//GEN-LAST:event_btn_MoverDelanteActionPerformed

    private void btn_MoverAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MoverAtrasActionPerformed
        if (lista.estaVacia()) {
            error(new IllegalStateException("La lista est\u00e1 vac\u00eda."));
            return;
        }
        int ultimoIndice = lista.obtenerTamano() - 1;
        int destino;
        if (posResaltada < 0 || posResaltada > ultimoIndice) {
            destino = ultimoIndice;           // sin cursor: empieza en ultimo
        } else if (posResaltada == 0) {
            registrarOperacion("Ya est\u00e1s en el primer nodo (pos 0).");
            JOptionPane.showMessageDialog(this, "Ya est\u00e1s en el primer nodo de la lista.",
                    "Mover atr\u00e1s", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            destino = posResaltada - 1;       // retrocede por el enlace anterior
        }
        moverCursor("Mover atr\u00e1s", destino);
    }//GEN-LAST:event_btn_MoverAtrasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GUI_Historial().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarFinal;
    private javax.swing.JButton btnAgregarInicio;
    private javax.swing.JButton btnAgregarPosicion;
    private javax.swing.JButton btnMostrarFinal;
    private javax.swing.JButton btnMostrarInicio;
    private javax.swing.JButton btnMostrarPosicion;
    private javax.swing.JButton btnMostrarValor;
    private javax.swing.JButton btnRemoverFinal;
    private javax.swing.JButton btnRemoverInicio;
    private javax.swing.JButton btnRemoverPosicion;
    private javax.swing.JButton btnVaciarFila;
    private javax.swing.JButton btn_MoverAtras;
    private javax.swing.JButton btn_MoverDelante;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelContenedorLista;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelLog;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JScrollPane scrollEstructura;
    private javax.swing.JTextArea txtLog;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtURL;
    // End of variables declaration//GEN-END:variables

}
