package Reporte;

import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import com.coffee.back.dao.AbstractDAO;
import javax.swing.JOptionPane;
import net.sf.jasperreports.view.JasperViewer;


public class GenerarReportes extends AbstractDAO{
    
    public void reporteVentas(String fecha_ini,String fecha_fin){
        
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject("UsuarioReporte.jasper");
            Map parametros=new HashMap();
            parametros.put("fecha_inicio",fecha_ini);
            parametros.put("fecha_fin",fecha_fin);
            JasperPrint j=JasperFillManager.fillReport(reporte, parametros, getConnection());
            JasperViewer jv=new JasperViewer(j,false);
            jv.setTitle("Reporte ventas");
            jv.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar el reporte "+e);
        }
    }
    
}
