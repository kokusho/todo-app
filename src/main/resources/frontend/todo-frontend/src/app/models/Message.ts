export class Message{
    message: string;
    type: MessageType;
}

export enum MessageType {
    Alert   = "alert",
    Danger  = "danger",
    Warning = "warning",
    Success = "success",
    Info    = "info",
}