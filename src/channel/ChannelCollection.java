package channel;

import error.ChannelNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Carol
 */
public class ChannelCollection {

    protected ArrayList<Channel> collection;
    protected String pathFile;
    protected String scriptsPath;

    public ChannelCollection(final String pathFile, final String scriptsPath)  {
        this.collection = new ArrayList();
        this.scriptsPath = scriptsPath;

        try {
            BufferedReader br = new BufferedReader(new FileReader(pathFile));
            String line = br.readLine();

            while (line != null) {
                try {
                    String[] parameter = line.split("\t");
                    Channel channel = new Channel(
                        scriptsPath,
                        Integer.parseInt(parameter[0]),
                        parameter[1],
                        parameter[2]
                    );
                    this.collection.add(channel);
                } catch (NumberFormatException ex) {
                    // skip failing channel
                }

                line = br.readLine();
            }
        } catch (IOException ex) {
            // failed to open source -> empty collection
        }
    }

    /**
     * Gets the specified channel given its ID.
     *
     * @param id
     * @return
     * @throws ChannelNotFoundException When channel was not found
     */
    public Channel getById(final int id) throws ChannelNotFoundException {
        Channel found = null;

        // TODO: sequential search -> optimize
        for (Channel c : this.collection) {
            if (c.getId() == id) {
                found = c;
                break;
            }
        }

        if (found == null) {
            throw new ChannelNotFoundException(String.format("El canal solicitado (ID:%i) no existe en la lista de canales disponibles.", id));
        }

        return found;
    }

    /**
     * Gets this collection structure, suitable for loops or direct
     * modifications.
     *
     * @return 
     */
    public ArrayList<Channel> getCollection() {
        return collection;
    }

    /**
     * The size of this collection.
     *
     * @return 
     */
    public int size() {
        return this.collection.size();
    }
}
