import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from './modal.component';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor(private modalService: NgbModal) { }

  // Usar este para acciones destructivas (Ej: Borrar)
  confirm(title: string, message: string, description: string): Promise<any>{
    const modal = this.modalService.open(ModalComponent);
    modal.componentInstance.title = title;
    modal.componentInstance.message = message;
    modal.componentInstance.description = description;
    
    modal.componentInstance.showCancelButton = true; 
    return modal.result;
  }

  // Usar este para avisos (Ej: Guardado exitoso, errores, advertencias)
  info(title: string, message: string, description: string = ''): Promise<any>{
    const modal = this.modalService.open(ModalComponent);
    modal.componentInstance.title = title;
    modal.componentInstance.message = message;
    modal.componentInstance.description = description;
    
    // Le indicamos al componente que NO muestre el botón Cancelar
    modal.componentInstance.showCancelButton = false; 
    return modal.result;
  }
}