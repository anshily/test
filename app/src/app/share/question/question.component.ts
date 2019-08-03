import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss'],
})
export class QuestionComponent implements OnInit {
    @Output() updateResult: EventEmitter<any> = new EventEmitter();
    @Input() questionId;
    question;
    answers;
    isRight;
    rightResult;
    userResult;
    showResult = false;
  constructor(private http: HttpClient) { }

  ngOnInit() {
      this.http.get('https://localhost:8888/question/detail?id=' + this.questionId).subscribe(res => {
          console.log(res);
          if (res['code'] === 0) {
              this.question = res['data']['detail'];
              this.answers = res['data']['answer'].map(item => {
                  item['isChecked'] = false;
                  return item;
              });
          }
      });
  }

    checkChange() {
        // let tmp = this.answers.filter(item =>  {
        //     return item.isChecked === true;
        // });
        // console.log(tmp);
        this.checkResult();
    }
    checkResult() {
        let isRight = true;
        let rightResult = [];
        let userResult = [];
        this.answers.forEach(item => {
            if (item['isChecked']){
                userResult.push(item['index_letter']);
            }
            if (item['result'] === 1) {
                rightResult.push(item['index_letter']);
            }
            // 答案项未选中 或者 选中项非答案 皆判错
            if ( (item['result'] === 1 && !item['isChecked']) || (item['isChecked'] && item['result'] === 0) ) {
                isRight = false;
            }
        });
        // console.log(isRight, userResult, rightResult);
        this.showResult = true;
        this.isRight = isRight;
        this.userResult = userResult;
        this.rightResult = rightResult;
        this.updateResult.emit({
            isRight: this.isRight,
            userResult: this.userResult,
            rightResult: this.rightResult,
            question: this.question
        });
    }
}
