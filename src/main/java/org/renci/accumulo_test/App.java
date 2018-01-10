package org.renci;

/**
 * Hello world!
 *

	public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
**/

import org.apache.accumulo.core.client.AccumuloException;

import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.client.admin.TableOperations;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.ColumnVisibility;
import org.apache.log4j.Logger;
import org.apache.hadoop.io.Text;

public class App {
  public static void main (String[] args) throws AccumuloException, AccumuloSecurityException,TableNotFoundException, TableExistsException{
  //public static void main(String[] args) throws AccumuloException, AccumuloSecurityException,TableNotFoundException, TableExistsException {
        // Constants
        String instanceName = "exogeni";
        String zooServers = "172.16.100.5,172.16.100.4,172.16.100.1"; // Provide list of zookeeper server here. For example, localhost:2181 
        String userName = "root"; // Provide username
        String password = "secret"; // Provide password
        // Connect
        //System.out.println("hi");


        Instance inst = new ZooKeeperInstance(instanceName,zooServers);
	System.out.println("checkpoint1");
        Connector conn = inst.getConnector(userName, password);
	System.out.println("checkpoint2");
       // Create our new table
/*
        String tableName = "testTable";
        TableOperations ops = conn.tableOperations();
         if (ops.exists(tableName)) {
            ops.delete(tableName);
        }
        ops.create(tableName);
*/
        // Use batch writer to write demo data
        String tableName = "trace";
        try{
        	BatchWriter bw = conn.createBatchWriter(tableName,1000000, 60000, 2);
        } catch (TableNotFoundException e) {
        	System.out.println("Accumulo table " + tableName + " doesn't exist and cannot be created.");
		} 
        
        // set values
        Text rowID = new Text("row1");
        Text colFam = new Text("colFam");
        Text colQual = new Text("colQual");
        // set value
        Value value = new Value("some-value".getBytes());
        // create new mutation and add rowID, colFam, colQual, and value
        Mutation mutation = new Mutation(rowID);
        
        mutation.put(colFam, colQual, value);
        // add the mutation to the batch writer
        bw.addMutation(mutation);
        // close the batch writer
        bw.close();
        
		System.out.println("checkpoint3");
    }
}
