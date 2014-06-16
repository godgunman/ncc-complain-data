//
//  OneComplainViewController.h
//  fakenews
//
//  Created by Golo on 14/6/6.
//  Copyright (c) 2014年 moskastudio. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OneComplainViewController : UIViewController
@property IBOutlet UIView *closeButtonView;
@property IBOutlet UIScrollView *oneComplainScrollView;
@property IBOutlet UILabel *channelNameLable;
@property IBOutlet UILabel *programNameLable;
@property IBOutlet UILabel *cidLable;
@property IBOutlet UILabel *complainDateLable;
@property IBOutlet UILabel *broadcastDateLable;
@property IBOutlet UILabel *broadcastTimeLable;
@property IBOutlet UILabel *complainTitleIndicatorLable;
@property IBOutlet UILabel *complainTitleLable;
@property IBOutlet UILabel *complainContentIndicatorLable;
@property IBOutlet UILabel *complainContentLable;
@property IBOutlet UILabel *replyContentIndicatorLable;
@property IBOutlet UILabel *replyContentLable;
@property IBOutlet UIImageView *dividingLine1ImageView;
@property IBOutlet UIImageView *dividingLine2ImageView;

@property NSMutableDictionary *selectedComplain;

@end
