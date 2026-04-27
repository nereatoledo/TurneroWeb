import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="modal-content-custom">
      
      <div class="modal-header border-0 pb-0">
        <h4 class="custom-title m-0" id="modal-title">{{title}}</h4>
        <button type="button" class="close-btn" aria-describedby="modal-title" (click)="modal.dismiss()">
          <i class="fa fa-times"></i>
        </button>
      </div>
      
      <div class="modal-body py-4">
        <p class="message-text">
          {{message}}
        </p>
        <p *ngIf="description" class="description-text mt-2">
          {{description}}
        </p>
      </div>
      
      <div class="modal-footer border-0 pt-0">
        <button *ngIf="showCancelButton" type="button" class="btn-back" (click)="modal.dismiss()">Cancelar</button>
        
        <button type="button" class="btn-purple-main" (click)="modal.close()">Aceptar</button>
      </div>

    </div>
  `,
  styles: [`
    .modal-content-custom {
      background: #ffffff;
      border-radius: 12px;
      padding: 5px;
    }

    .custom-title {
      color: #000000;
      font-family: 'Hackensack', 'Roboto Slab', serif;
      font-weight: bold;
      font-size: 1.35rem;
    }

    .close-btn {
      background: transparent;
      border: none;
      font-size: 1.2rem;
      color: #6c757d;
      cursor: pointer;
      transition: color 0.2s ease;
      padding: 0;
    }

    .close-btn:hover {
      color: #dc3545; 
    }

    .message-text {
      color: #2d3748;
      font-size: 1.05rem;
      margin: 0;
      font-weight: 500;
    }

    .description-text {
      color: #6c757d;
      font-size: 0.9rem;
      margin: 0;
    }

    .btn-purple-main {
      background-color: #8923dc;
      color: #ffffff;
      padding: 8px 20px;
      border-radius: 8px;
      font-weight: 500;
      transition: all 0.3s ease;
      box-shadow: 0 4px 10px rgba(137, 35, 220, 0.25);
      border: none;
      cursor: pointer;
    }

    .btn-purple-main:hover {
      background-color: #6c1ab3;
      transform: translateY(-2px);
      box-shadow: 0 6px 15px rgba(137, 35, 220, 0.35);
    }

    .btn-back {
      background-color: #f8f9fa;
      color: #4a5568;
      border: 1.5px solid #e2e8f0;
      padding: 8px 20px;
      border-radius: 8px;
      font-weight: 500;
      transition: all 0.2s ease;
      cursor: pointer;
    }

    .btn-back:hover {
      background-color: #e2e8f0;
      color: #2d3748;
    }
  `]
})
export class ModalComponent {
  constructor(public modal: NgbActiveModal) {}
  
  title = '';
  message = '';
  description = '';
  showCancelButton = true; // Por defecto asumimos que queremos confirmar una acción
}