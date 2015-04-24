/*
    Copyright (C) 2015   Jesper Zedlitz <jesper@zedlitz.de>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

*/
package tingeltangel.cli.cmds;

import tingeltangel.ReadYamlFile;
import tingeltangel.cli.CliCommand;
import tingeltangel.cli.CliSwitch;
import tingeltangel.core.Book;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Assemble extends CliCommand {
    @Override
    public String getName() {
        return "assemble";
    }

    @Override
    public String getDescription() {
        return "Erstellt aus einer yaml-Datei eine ouf-Datei.";
    }

    @Override
    public Map<String, CliSwitch> getSwitches() {
        CliSwitch[] list = {
                new CliSwitch() {
                    @Override
                    public String getName() {
                        return("i");
                    }

                    @Override
                    public String getDescription() {
                        return("setzt den Namen der Eingabedatei");
                    }

                    @Override
                    public boolean hasArgument() {
                        return(true);
                    }

                    @Override
                    public boolean isOptional() {
                        return(false);
                    }

                    @Override
                    public String getLabel() {
                        return("Eingabedatei");
                    }

                    @Override
                    public String getDefault() {
                        return(null);
                    }

                    @Override
                    public boolean acceptValue(String value) {
                        return(!value.isEmpty());
                    }
                }
        }       ;


        return list2map(list);
    }

    @Override
    public void execute(Map<String, String> args) throws Exception {
        File inputFile = new File(args.get("i"));

        if( inputFile.canRead()) {
            ReadYamlFile ryf = new ReadYamlFile();
            Book book = ryf.read(inputFile);
            book.export( new File("."));
        } else {
            System.err.println("Fehler beim Lesen der Eingabedatei.");
        }
    }
}
