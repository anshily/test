import { Component, OnInit } from '@angular/core';
import {NavigationEnd, Router, RouterEvent} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {MenuController, ModalController, ToastController} from '@ionic/angular';
import {HomePage} from '../home/home.page';
import {filter} from 'rxjs/internal/operators';
import {StatusBar} from '@ionic-native/status-bar/ngx';

@Component({
  selector: 'app-feeds',
  templateUrl: './feeds.page.html',
  styleUrls: ['./feeds.page.scss'],
})
export class FeedsPage implements OnInit {
    question;
    answers;
    isRight;
    rightResult;
    userResult;
    showResult = false;
    remoteVideoSourceArr = [];
    public storageBaseUrl = ROOT_URL + 'storage';
  constructor(private statusBar: StatusBar,
              private router: Router,
              private http: HttpClient,
              public toastController: ToastController,
              private menu: MenuController,
              private modalController: ModalController
  ) {
      this.router.events.pipe(
          filter(e => e instanceof NavigationEnd)
      ).subscribe(e => {
          console.log(e);
          if (e['url'] == '/feeds') {
              this.checkWelcome();
          }
      });
      // 43110
      // this.http.post('http://47.97.189.68:5102/front/api/errorQuestion/getQuestionDetail', {
      //     args: {questionBankId: 44395},
      //     deviceinfo: 'Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) ' +
      //     'AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Mobile Safari/537.36',
      //     token: 'dreamtouch'}).subscribe(res => {
      //         console.log(res);
      // });
  }

  ngOnInit() {
      this.http.get('https://localhost:8888/question/detail?id=45380').subscribe(res => {
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
    checkWelcome() {
        if (localStorage.getItem('anshi_id') && localStorage.getItem('anshi_id') !== '') {
            console.log('test');
            // this.router.navigate(['/mine']).then(res => {
            //     console.log(res);
            // });
            // this.router.navigate(['/welcome']).then(res => {
            //     console.log(res);
            // });
        }else {
            if (localStorage.getItem('welcomeTime') && localStorage.getItem('welcomeTime') == '1') {
                console.log('we');
            }else {
                localStorage.setItem('welcomeTime','1');
                this.router.navigate(['/welcome']).then(res => {
                    console.log(res);
                });
            }
        }
    }

    goMine() {
        if (localStorage.getItem('anshi_id') && localStorage.getItem('anshi_id') !== '') {
            this.router.navigate(['/mine']).then(res => {
                console.log(res);
            });
        }else {
            this.router.navigate(['/login']).then(res => {
                console.log(res);
            });
        }
    }

    async presentModal(e) {
    console.log(e);
        const modal = await this.modalController.create({
            component: HomePage,
            componentProps: {insertId: e['id']},
        });
        await modal.present();
        const { data } = await modal.onDidDismiss();
        console.log(data);
        // return await modal.present();
    }

    noSupport() {
        this.presentToast('功能暂未开放！').then(() => {
            console.log('功能暂未开放！');
            this.menu.close('third');
        });
    }

    openThird() {
        this.menu.enable(true, 'third');
        this.menu.open('third');
    }


    goSearch() {
        console.log('功能暂未开放！');
        this.noSupport();
    }

    async presentToast(msg) {
        const toast = await this.toastController.create({
            message: msg,
            duration: 2000
        });
        toast.present();
    }


    goNote() {
        this.router.navigate(['/note']).then( () => {
            console.log('note');
        });
    }

    checkChange() {
      let tmp = this.answers.filter(item =>  {
          return item.isChecked === true;
      });
      console.log(tmp);
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
      this.userResult = JSON.stringify(userResult) ;
      this.rightResult = JSON.stringify(rightResult);
    }

    updateResult(event) {
      console.log(event);
    }
}
