package build_structure;

import ui.Global;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Build {
    private String mainFolder = Global.getPath() + File.separator + "BCL_CL";
    //> Windows
    //>> Internal Paths
    final private String winEthminerBinaryRes = "/binaries/windows/ethminer.exe";
    final private String winStaticNodeRes = "/json/static-nodes.json";//=Mac
    final private String winGenesisRes = "/json/genesis.json"; //==Mac
    final private String winGethBinaryRes = "/binaries/windows/geth.exe";
    final private String winStartCommandRes = "/commands/windows/start.cmd";
    final private String winGethCommandRes = "/commands/windows/geth.cmd";
    final private String winEthMinerCommandRes = "/commands/windows/ethminer.cmd";
    //>> External Paths
    private String winEthminerBinary = Global.getPath() + File.separator + "ethminer.exe";
    private String winGenesis = Global.getPath() + File.separator + "genesis.json";//==Mac
    private String winGethBinary = Global.getPath() + File.separator + "geth.exe";
    private String winStartCommand = Global.getPath() + File.separator + "start.cmd";
    private String winGethCommand = Global.getPath() + File.separator + "geth.cmd";
    private String winEthMinerCommand = Global.getPath() + File.separator + "ethminer.cmd";
    private String winStaticNode = Global.getPath() + File.separator + "BCL_Node" + File.separator
            + "geth" + File.separator + "static-nodes.json";//=Mac
    //> Mac
    //>> Commands

    //>> Internal Paths
    final private String macStaticNodeRes = "/json/static-nodes.json";//=Win
    final private String macEthminerBinaryRes = "/binaries/mac/ethminer";
    final private String macGenesisRes = "/json/genesis.json"; //==Win
    private final String macGethBinaryRes = "/binaries/mac/geth";
    private final String macStartCommandRes = "/commands/mac/start.command";
    private final String macGethCommandRes = "/commands/mac/geth.command";
    private final String macEthMinerCommandRes = "/commands/mac/ethminer.command";
    //>> External Paths
    private String macEthminerBinary = Global.getPath() + File.separator + "ethminer";
    private String macGenesis = Global.getPath() + File.separator + "genesis.json";//==Win
    private String macGethBinary = Global.getPath() + File.separator + "geth";
    private String macStartCommand = Global.getPath() + File.separator + "start.command";
    private String macGethCommand = Global.getPath() + File.separator + "geth.command";
    private String macEthMinerCommand = Global.getPath() + File.separator + "ethminer.command";
    private String macStaticNode = Global.getPath() + File.separator + "BCL_Node" + File.separator
            + "geth" + File.separator + "static-nodes.json";//=Win

    public void BCLFolder() {
        try {
            Path path = Paths.get(mainFolder);
            Files.createDirectories(path);
        } catch (IOException e) {
            System.out.println("Unable To Create BCL_CL Folder");
            e.printStackTrace();
        }
    }

    public void binaries() {
        if (Global.getOS().contains("win")) {
            Utils.export_resource(winGethBinaryRes, winGethBinary);
            Utils.export_resource(winGenesisRes, winGenesis);
            Utils.export_resource(winEthminerBinaryRes, winEthminerBinary);
            Utils.export_resource(winStaticNodeRes, winStaticNode);
        }
        if (Global.getOS().contains("mac")) {
            Utils.export_resource(macGethBinaryRes, macGethBinary);
            Utils.export_resource(macGenesisRes, macGenesis);
            Utils.export_resource(macEthminerBinaryRes, macEthminerBinary);
            Utils.export_resource(macStaticNodeRes, macStaticNode);
        }
    }

    public void commands() {
        if (Global.getOS().contains("win")) {
            Utils.export_resource(winStartCommandRes, winStartCommand);
            Utils.export_resource(winGethCommandRes, winGethCommand);
            Utils.export_resource(winEthMinerCommandRes, winEthMinerCommand);
        }
        //NOTE: Not else for future linux support
        if (Global.getOS().contains("mac")) {
            Utils.export_resource(macStartCommandRes, macStartCommand);
            Utils.export_resource(macGethCommandRes, macGethCommand);
            Utils.export_resource(macEthMinerCommandRes, macEthMinerCommand);
        }
    }

    public void start_command() {

    }
}
