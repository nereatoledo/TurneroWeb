import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from './modal.component';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor(private modalService: NgbModal) { }

  confirm(title: string, message: string, description: string, detailsList?: any[]): Promise<any>{
    const modal = this.modalService.open(ModalComponent, { backdrop: 'static', keyboard: false });
    modal.componentInstance.title = title;
    modal.componentInstance.message = message;
    modal.componentInstance.description = description;
    
    if (detailsList) {
        modal.componentInstance.detailsList = detailsList;
    }
    
    modal.componentInstance.showCancelButton = true; 
    return modal.result;
  }

  info(title: string, message: string, description: string = ''): Promise<any>{
    const modal = this.modalService.open(ModalComponent, { backdrop: 'static', keyboard: false });
    modal.componentInstance.title = title;
    modal.componentInstance.message = message;
    modal.componentInstance.description = description;
    
    modal.componentInstance.showCancelButton = false; 
    return modal.result;
  }
}