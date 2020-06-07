import { Injectable } from '@angular/core';
import { Message } from './models/Message';

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
}
