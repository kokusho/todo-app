import { User } from './User';

export class Todo{
    id: number;
    title: string;
    assignedUser: User;
    done: boolean;
}