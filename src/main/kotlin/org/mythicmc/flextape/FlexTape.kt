package org.mythicmc.flextape

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.KickedFromServerEvent
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import java.nio.file.Path

@Plugin(
    id = "flextape",
    name = "FlexTape",
    authors = ["retrixe"],
    version = BuildMetadata.VERSION,
    description = BuildMetadata.DESCRIPTION,
    url = "https://github.com/mythicmc/flextape",
    dependencies = []
)
class FlexTape @Inject constructor(
    val server: ProxyServer,
    val logger: Logger,
    @DataDirectory val dataDirectory: Path
) {
    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        if (Math.random() < 0.9) return
        logger.info("Hi, Phil Swift here with Flex Tape! The super-strong waterproof tape! " +
                "That can instantly patch, bond, seal, and repair! Flex tape is no ordinary tape;" +
                " its triple thick adhesive virtually welds itself to the surface," +
                " instantly stopping the toughest leaks. Leaky pipes can cause major damage," +
                " but Flex Tape grips on tight and bonds instantly! Plus," +
                " Flex Tape’s powerful adhesive is so strong, it even works underwater!" +
                " Now you can repair leaks in pools and spas in water without draining them!" +
                " Flex Tape is perfect for marine, campers and RVs! Flex Tape is super strong," +
                " and once it's on, it holds on tight! And for emergency auto repair, Flex Tape" +
                " keeps its grip, even in the toughest conditions!" +
                " Big storms can cause big damage, but Flex Tape comes super wide," +
                " so you can easily patch large holes. To show the power of Flex Tape," +
                " I sawed this boat in half! And repaired it with only Flex Tape!" +
                " Not only does Flex Tape’s powerful adhesive hold the boat together," +
                " but it creates a super strong water tight seal, so the inside is completely dry!" +
                " Yee-doggy! Just cut, peel, stick and seal!" +
                " Imagine everything you can do with the power of Flex Tape!")
    }

    @Subscribe
    fun onKickedFromServer(event: KickedFromServerEvent) {
        if (event.serverKickReason.isEmpty) return
        event.result = KickedFromServerEvent
            .DisconnectPlayer
            .create(event.serverKickReason.get())
    }
}
