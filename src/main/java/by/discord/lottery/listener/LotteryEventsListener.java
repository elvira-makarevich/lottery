package by.discord.lottery.listener;

import by.discord.lottery.entity.Member;
import by.discord.lottery.service.LotteryService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import static by.discord.lottery.util.ConstStringParameter.*;

@RequiredArgsConstructor
@Component
public class LotteryEventsListener extends ListenerAdapter {

    private final LotteryService lotteryService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String eventMessage = event.getMessage().getContentRaw().toLowerCase().strip();

        switch (eventMessage) {
            case START_LOTTERY:
                if (event.getMember().hasPermission(Permission.valueOf(ADMINISTRATOR))) {
                    event.getChannel().sendMessage(lotteryService.startLottery()).queue();
                } else {
                    event.getChannel().sendMessage(REJECT_OPERATION_FOR_PARTICIPANT).queue();
                }
                break;
            case PARTICIPATE:
                if (!event.getMember().hasPermission(Permission.valueOf(ADMINISTRATOR))) {
                    event.getChannel().sendMessage(lotteryService.
                            participate(new Member(event.getAuthor().getId(), event.getAuthor().getName()))).queue();
                } else {
                    event.getChannel().sendMessage(REJECT_OPERATION_FOR_ADMINISTRATOR).queue();
                }
                break;
            case ALL_MEMBERS:
                event.getChannel().sendMessage(lotteryService.getLotteryMembers()).queue();
                break;
            case END_LOTTERY:
                if (event.getMember().hasPermission(Permission.valueOf(ADMINISTRATOR))) {
                    event.getChannel().sendMessage(lotteryService.endLottery()).complete().addReaction(REACTION_FOR_WINNER).queue();
                } else {
                    event.getChannel().sendMessage(REJECT_OPERATION_FOR_PARTICIPANT).queue();
                }
                break;
            default:
                event.getChannel().sendMessage(NON_EXISTENT_COMMAND).queue();
                break;
        }
    }
}
