import { Injectable } from '@angular/core';
import { Message, MessageType } from './models/Message';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  messages: Message[] = [];

  add(message: Message){
    this.messages.push( message );
  }

  clear() {
    this.messages = [];
  }

  delete(index: number){
    this.messages.splice(index, 1);
  }

  log(message: string, m_type: MessageType) {
    let msg: Message = {
      message: message,
      type: m_type
    }
    this.add(msg);
  }
}
