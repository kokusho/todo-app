export class Page<T>{
    content: T[];
    totalPages: number;
    totalElements: number;
    first: boolean;
    last: boolean;
    number: number;
    size: number;
    empty: boolean;
}