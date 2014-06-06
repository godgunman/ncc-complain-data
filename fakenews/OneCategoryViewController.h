//
//  OneCategoryViewController.h
//  fakenews
//
//  Created by Golo on 14/6/5.
//  Copyright (c) 2014年 moskastudio. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "rotatingItem.h"

@interface OneCategoryViewController : UIViewController<NSURLConnectionDelegate,UITableViewDataSource,UITableViewDelegate>
@property NSMutableDictionary *selectedChannel;
@property NSMutableDictionary *selectedCategory;
@property int rank;
@property IBOutlet UIImageView *logoImageView;
@property IBOutlet UILabel *rankLable;
@property IBOutlet UIImageView *rankBackgroundImageView;
@property IBOutlet UILabel *channelNameLable;
@property IBOutlet UILabel *channelNumberLable;
@property IBOutlet UILabel *categoryNameLable;
@property IBOutlet UILabel *categoryNumberLable;

@property rotatingItem *rotatingItemImageView;
@property NSMutableArray *complainArray;
@property IBOutlet UITableView *complainTableView;

@end
