package com.tapi.tester;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import com.sforce.soap.tooling.Connector;
import com.sforce.soap.tooling.SaveResult;
import com.sforce.soap.tooling.ToolingConnection;
import com.sforce.soap.tooling.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.soap.tooling.sobject.OperationLog;
import com.sforce.soap.tooling.OperationParameters;
import com.sforce.soap.tooling.Territory2RunTerritoryRulesPayload;

/**
 * Tooling API tester app.
 */
public class App {

  public static ToolingConnection toolingConnection;
  private static AppConfig config;
  private static Set<Method> methods;

  public static void main( String[] args ) throws Exception {
	  
    // Check how many arguments were passed in
   if (args.length == 0) {
      System.out.println("Need to specify which model or territory you want to run against. Use '-h' for help");
      System.exit(0);
    }

   if (args[0].equalsIgnoreCase("-h")) {
      printHelpMenu();
      System.exit(0);
    }

    config = new AppConfig();
    createToolingConnection(config.getUsername(), config.getPassword(), config.getToolingEndpoint());

    if (args[0].equalsIgnoreCase("-m")){
      if (args.length == 1){
        System.out.println("Need to specify territory Model ID");
        System.exit(0);
      }
      createOperationLog(args[1]);
    }

    if (args[0].equalsIgnoreCase("-t")) {
      if (args.length == 1) {
        System.out.println("Need to specify territory Model Id and territory Id.");
        System.exit(0);
      }
      if (args.length == 2){
        System.out.println("Need to specify territory Id");
        System.exit(0);
      }
      createOperationLog(args[1], args[2]);
    }


  }

  /**
   * Prints the app's running options.
   */
  private static void printHelpMenu() {
    System.out.println( "--- Run Assignment Rule App ---");
    System.out.println( "java -jar target/tooling-api-tester-1.0-SNAPSHOT.jar -h, Help.");
    System.out.println( "java -jar target/tooling-api-tester-1.0-SNAPSHOT.jar -m [territoryModelID], Run assignment rule against whole model.");
    System.out.println( "java -jar target/tooling-api-tester-1.0-SNAPSHOT.jar -t [territoryModelID] [territoryID], Run assignment rule against territory.");
  }



  private static void createToolingConnection(final String username, final String password, final String loginUrl) throws ConnectionException, IOException {
    final ConnectorConfig loginConfig = new ConnectorConfig();
    loginConfig.setAuthEndpoint(loginUrl);
    loginConfig.setManualLogin(true);

    String sessionId = config.getSessionId();
    ConnectorConfig toolingConfig = new ConnectorConfig();

    if (sessionId.isEmpty()) {
      loginConfig.setServiceEndpoint(loginUrl);
      ToolingConnection toolingConn = com.sforce.soap.tooling.Connector.newConnection(loginConfig);
      com.sforce.soap.tooling.LoginResult tlr = toolingConn.login(username, password);

      toolingConfig.setSessionId(tlr.getSessionId());
      toolingConfig.setServiceEndpoint(tlr.getServerUrl());
    } else {
      toolingConfig.setSessionId(sessionId);
      toolingConfig.setServiceEndpoint(loginUrl);
    }

    toolingConnection = Connector.newConnection(toolingConfig);
  }

  public static void createOperationLog(String territoryModelId) throws ConnectionException {
    OperationLog op = new OperationLog();

    op.setType("RunTerritoryRules");
    Territory2RunTerritoryRulesPayload territoryPayload = new Territory2RunTerritoryRulesPayload();
    territoryPayload.setKeyPrefix("001");
    territoryPayload.setTerritoryId(null);
    territoryPayload.setTerritoryModelId(territoryModelId);
    OperationParameters operationParameters = new OperationParameters();
    operationParameters.setPayload(territoryPayload);
    op.setParameters(operationParameters);

    SaveResult[] sr = toolingConnection.create(new SObject[] {op});
    Utils.printResults(sr, "OperationLog create");

  }

  public static void createOperationLog(String territoryModelId, String territoryId) throws ConnectionException {
    OperationLog op = new OperationLog();

    op.setType("RunTerritoryRules");
    Territory2RunTerritoryRulesPayload territoryPayload = new Territory2RunTerritoryRulesPayload();
    territoryPayload.setKeyPrefix("001");
    territoryPayload.setTerritoryId(territoryId);
    territoryPayload.setTerritoryModelId(territoryModelId);
    OperationParameters operationParameters = new OperationParameters();
    operationParameters.setPayload(territoryPayload);
    op.setParameters(operationParameters);

    SaveResult[] sr = toolingConnection.create(new SObject[] {op});
    Utils.printResults(sr, "OperationLog create");
  }


  
}
