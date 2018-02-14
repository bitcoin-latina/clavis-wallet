package build_structure;

import javafx.scene.control.Alert;
import ui.Global;
import ui.Init;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Build {
    /**
     * This Class Is Used To Insert Config Files And Binaries
     */
    private static final Logger LOGGER = Logger.getLogger(Init.class.getName());
    private String mainFolder = Global.getPath() + File.separator + "BCL_CL";
    //>> External Paths
    final private String winEthminerBinary = Global.getPath() + File.separator + "ethminer.exe";
    final private String winGenesis = Global.getPath() + File.separator + "genesis.json";//==Mac
    final private String winGethBinary = Global.getPath() + File.separator + "geth.exe";
    final private String winStartCommand = Global.getPath() + File.separator + "start.cmd";
    final private String winGethCommand = Global.getPath() + File.separator + "geth.cmd";
    final private String winEthMinerCommand = Global.getPath() + File.separator + "ethminer.cmd";
    final private String winStaticNode = Global.getPath() + File.separator + "BCL_Node" + File.separator
            + "geth" + File.separator + "static-nodes.json";//=Mac
    //>> External Paths
    final private String macEthminerBinary = Global.getPath() + File.separator + "ethminer";
    final private String macGenesis = Global.getPath() + File.separator + "genesis.json";//==Win
    final private String macGethBinary = Global.getPath() + File.separator + "geth";
    final private String macStartCommand = Global.getPath() + File.separator + "start.command";
    final private String macGethCommand = Global.getPath() + File.separator + "geth.command";
    final private String macEthMinerCommand = Global.getPath() + File.separator + "ethminer.command";
    final private String macStaticNode = Global.getPath() + File.separator + "BCL_Node" + File.separator
            + "geth" + File.separator + "static-nodes.json";//=Win
    //>>External Paths
    final private String linGenesis = Global.getPath() + File.separator + "genesis.json";//==Win
    final private String linGethBinary = Global.getPath() + File.separator + "geth";
    final private String linStartCommand = Global.getPath() + File.separator + "start.sh";
    final private String linGethCommand = Global.getPath() + File.separator + "geth.sh";
    final private String linEthMinerCommand = Global.getPath() + File.separator + "ethminer.sh";
    final private String linStaticNode = Global.getPath() + File.separator + "BCL_Node" + File.separator
            + "geth" + File.separator + "static-nodes.json";//=Win

    public void BCLFolder() {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Adding BCL Folder");
        try {
            Path path = Paths.get(mainFolder);
            Files.createDirectories(path);
            //Create BCL_Node Folder
            Path path2 = Paths.get(mainFolder+File.separator+"BCL_Node");
            Files.createDirectories(path2);
            //Create Geth Folder For Static Nodes
            Path path3 = Paths.get(mainFolder+File.separator+"BCL_Node"+File.separator+"geth");
            Files.createDirectories(path3);
        } catch (IOException e) {
            LOGGER.warning("COULDN'T CREATE BCL_FOLDER \n\n" + e.getStackTrace());
            LOGGER.warning("Attempted creating BCL Folder at " + mainFolder);
            //Create Alert ->
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Build BCL_CL Folder ");
            alert.setContentText("Please Report This Issue W/ Your Log File @ https://discord.gg/kDzP4P8");
            alert.show();
        }
    }

    public void binaries() {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Adding Binaries");
        LOGGER.info("PATH IS " + Global.getPath());
        if (Global.getOS().contains("win")) {
            String winGethBinaryRes = "binaries"+File.separator+"windows"+File.separator+"geth.exe";
            Utils.export_resource(winGethBinaryRes, winGethBinary);
            LOGGER.info("Windows Geth Added");
            String winGenesisRes = "json"+File.separator+"genesis.json";
            Utils.export_resource(winGenesisRes, winGenesis);
            LOGGER.info("Windows Genesis File Added");
//            String winEthminerBinaryRes = "binaries"+File.separator+"windows"+File.separator+"ethminer.exe";
//            Utils.export_resource(winEthminerBinaryRes, winEthminerBinary);
//            LOGGER.info("Windows Ethminer Added");
            String winStaticNodeRes = "json"+File.separator+"static-nodes.json";
            Utils.export_resource(winStaticNodeRes, winStaticNode);
        } else if (Global.getOS().contains("mac")) {
            String macGethBinaryRes = "binaries"+File.separator+"mac"+File.separator+"geth";
            Utils.export_resource(macGethBinaryRes, macGethBinary);
            LOGGER.info("Mac Geth Added");
            String macGenesisRes = "json"+File.separator+"genesis.json";
            Utils.export_resource(macGenesisRes, macGenesis);
            LOGGER.info("Mac Genesis File Added");
//            String macEthminerBinaryRes = "binaries"+File.separator+"mac"+File.separator+"ethminer";
//            Utils.export_resource(macEthminerBinaryRes, macEthminerBinary);
//            LOGGER.info("Mac Ethminer Added");
            String macStaticNodeRes = "json"+File.separator+"static-nodes.json";
            Utils.export_resource(macStaticNodeRes, macStaticNode);
        }
        else if (Global.getOS().contains("lin")){
            String linGethBinaryRes = "binaries"+File.separator+"linux"+File.separator+"geth";
            Utils.export_resource(linGethBinaryRes, linGethBinary);
            LOGGER.info("Lin Geth Added");
            String linGenesisRes = "json"+File.separator+"genesis.json";
            Utils.export_resource(linGenesisRes, linGenesis);
            LOGGER.info("Lin Genesis File Added");
            String linStaticNodeRes = "json"+File.separator+"static-nodes.json";
            Utils.export_resource(linStaticNodeRes, linStaticNode);
        }
        //Linux Support Coming...
    }

    public void commands() {
        if (Global.getOS().contains("win")) {
            String winStartCommandRes = "commands"+File.separator+"windows"+File.separator+"start.cmd";
            Utils.export_resource(winStartCommandRes, winStartCommand);
            String winGethCommandRes = "commands"+File.separator+"windows"+File.separator+"geth.cmd";
            Utils.export_resource(winGethCommandRes, winGethCommand);
            String winEthMinerCommandRes = "commands"+File.separator+"windows"+File.separator+"ethminer.cmd";
            Utils.export_resource(winEthMinerCommandRes, winEthMinerCommand);
        } else if (Global.getOS().contains("mac")) {
            String macStartCommandRes = "commands"+File.separator+"mac"+File.separator+"start.command";
            Utils.export_resource(macStartCommandRes, macStartCommand);
            String macGethCommandRes = "commands"+File.separator+"mac"+File.separator+"geth.command";
            Utils.export_resource(macGethCommandRes, macGethCommand);
            String macEthMinerCommandRes = "commands"+File.separator+"mac"+File.separator+"ethminer.command";
            Utils.export_resource(macEthMinerCommandRes, macEthMinerCommand);
        }
        else if (Global.getOS().contains("lin")){
            String linStartCommandRes = "commands"+File.separator+"linux"+File.separator+"start.sh";
            Utils.export_resource(linStartCommandRes, linStartCommand);
            String linGethCommandRes = "commands"+File.separator+"linux"+File.separator+"geth.sh";
            Utils.export_resource(linGethCommandRes, linGethCommand);
            String linEthMinerCommandRes = "commands"+File.separator+"linux"+File.separator+"ethminer.sh";
            Utils.export_resource(linEthMinerCommandRes, linEthMinerCommand);
        }
        //Linux Support Coming...
    }
}
