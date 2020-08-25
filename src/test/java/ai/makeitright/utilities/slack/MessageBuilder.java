package ai.makeitright.utilities.slack;

public class MessageBuilder {
    private String channel;
    private String text;
    private String username;
    private String icon_url;
    private String icon_emoji;

    public MessageBuilder() {
    }

    public MessageBuilder(String text) {
        this.text = text;
    }

    public MessageBuilder(String channel, String text) {
        this.channel = channel;
        this.text = text;
    }

    public Message build() {
        return new Message(this);
    }

    public String getChannel() {
        return channel;
    }

    public MessageBuilder setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getText() {
        return text;
    }

    public MessageBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MessageBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getIconUrl() {
        return icon_url;
    }

    public MessageBuilder setIconUrl(String icon_url) {
        this.icon_url = icon_url;
        return this;
    }

    public String getIconEmoji() {
        return icon_emoji;
    }

    public MessageBuilder setIconEmoji(String icon_emoji) {
        this.icon_emoji = icon_emoji;
        return this;
    }

}
