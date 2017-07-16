package com.cfao.app.reports;

/**
 * Created by JP on 7/14/2017.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.cfao.app.util.AlertUtil;
import com.cfao.app.util.Report;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author javaQuery
 * @date 24nd November, 2015
 * @Github: https://github.com/javaquery/Examples
 */
public class JasperTableExample extends Report{

    public JasperTableExample() {
        try {


            /* List to hold Items */
            List<Item> listItems = new ArrayList<Item>();

            /* Create Items */
            Item iPhone = new Item();
            iPhone.setName("iPhone 6S");
            iPhone.setPrice(65000.00);

            Item iPad = new Item();
            iPad.setName("iPad Pro");
            iPad.setPrice(70000.00);

            /* Add Items to List */
            listItems.add(iPhone);
            listItems.add(iPad);


            /* Map to hold Jasper report Parameters */
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ItemDataSource", listItems);
            showReport("reports/table.jrxml", parameters);
        }catch(Exception ex){
            AlertUtil.showErrorMessage(ex);
            ex.printStackTrace();
        }
    }
}
class Item {

    private String name;
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // getter-setter
}