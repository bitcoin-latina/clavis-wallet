# Clavis Wallet
******************************************************************
GUI Wallet for BitcoinLatina

Version: 1.0 Alpha

Notes: Welcome all to the Clavis Wallet. This software is an Alpha
release and is generally untested. With that being said, there may
be some confusion on how to use this wallet. We hope to clear up 
that confusion here with a few points below:
******************************************************************

1.) This program opens up an instance of a terminal. This terminal
is your view into the blockchain. The wallet is interfacing with
this terminal and if exited, the wallet will not function properly.

2.) Upon Installation you may be asked to let our wallet through
your firewall. This is normal behavior as our application must be
connected to the internet.

3.) **DO NOT** lose your password. This is a universal truth with
blockchain technology and if you lose your password, it cannot 
be recovered.

******************************************************************

How to Install:

MAC: Run "Clavis Wallet.dmg", drag Clavis Wallet to your Applications folder, and discard the DMG.

WIN: You may place the file 'Clavis Wallet.exe' wherever you like. 
It is recommended that you place it within your program files.
Double click to launch

LIN: Coming Soon!

******************************************************************

Important Information For Advanced Users:
Wallet Files are located at *Home Dir*/BCL_CL/BCL_Node

******************************************************************

## Known Bugs:

* Syncing Percentage
* UI Inconsistencies

### Windows Sending Bug
******************************************************************
If you cannot send from your wallet, the following may be occuring.

On certain Windows machines, the Scrypt implementation in Bouncycastle, a Web3J dependency, runs poorly. When we say poorly, we mean *really poorly*. So poorly in fact, that it can temporarily push memory usage in Java beyond the default allocated memory heap. We are working on solution for this, but in the mean time, the best work around is to allocate more memory to Clavis Wallet.

To do this, you will need to run the wallet as a jar file with a flag to add more memory to the heap. The latest jar file can be found on our releases page.

Open cmd, and run the following:

`java -Xmx1024m -jar <path to clavis wallet.jar>`

You will now be able to send! Place this command in a batch (.bat) file to automate launching this way.
