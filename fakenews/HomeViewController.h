//
//  ViewController.h
//  LinHongLing
//
//  Created by Golo on 13/9/8.
//  Copyright (c) 2013年 moskastudio. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "rotatingItem.h"


@interface HomeViewController : UIViewController<UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate,NSURLConnectionDelegate>
@property rotatingItem *rotatingItemImageView;
@property NSMutableArray *channelArray;
@property IBOutlet UITableView *channelTableView;
@property IBOutlet UILabel *pendingNumberLabel;
@property NSString *pendingNumber;

@end
