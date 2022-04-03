package wtf.sentinel.antiwebhook.listener;

import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Serpent
 * @since 30/10/2021
 **/
public class WebhookListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(event.getMessage().isWebhookMessage()) {
            for(Webhook webhook : event.getGuild().retrieveWebhooks().complete()) {
                webhook.delete().queue();

                AuditLogPaginationAction auditLogs = event.getGuild().retrieveAuditLogs();
                auditLogs.stream().filter(auditLogEntry -> auditLogEntry.getType() == ActionType.WEBHOOK_CREATE).forEach(auditLogEntry -> {
                    if(auditLogEntry.getTargetIdLong() == webhook.getIdLong()) {
                        if (auditLogEntry.getUser() != null) {
                            final Member member = event.getGuild().getMember(auditLogEntry.getUser());

                            member.getRoles()
                                    .forEach(role -> event.getGuild().removeRoleFromMember(member, role).queue());
                        }
                    }
                });
            }
        }
    }
}