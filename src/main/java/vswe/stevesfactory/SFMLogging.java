package vswe.stevesfactory;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import org.lwjgl.Sys;

import java.util.concurrent.ThreadLocalRandom;

public class SFMLogging {
    public static final Logger LOGGER = LogManager.getLogger("SteveFactoryManager");

    public static void reportIncident(Exception ex, String location, Iterable<EntityPlayerMP> receivers) {
        String incidentId = "" + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(1000);
        Message message = new ParameterizedMessage("Exception in {}. Incident ID {}.", new Object[] {location, incidentId}, ex);
        LOGGER.error(message);
        for (EntityPlayerMP player : receivers) {
            player.addChatMessage(new ChatComponentTranslation("chat.sfm.ContainerInternalServerError", incidentId));
        }
    }
}
